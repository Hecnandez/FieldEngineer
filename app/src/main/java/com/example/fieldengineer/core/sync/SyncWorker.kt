package com.example.fieldengineer.core.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fieldengineer.core.data.remote.api.SyncApiService
import com.example.fieldengineer.core.data.remote.dto.SyncPushRequestDto
import com.example.fieldengineer.core.data.repository.AssetRepositoryImpl
import com.example.fieldengineer.core.data.repository.ConsumedPartRepositoryImpl
import com.example.fieldengineer.core.data.repository.InventoryRepositoryImpl
import com.example.fieldengineer.core.data.toDto
import com.example.fieldengineer.core.data.toEntity
import com.example.fieldengineer.core.data.repository.PartCatalogRepositoryImpl
import com.example.fieldengineer.core.data.repository.WorkOrderRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val syncApiService: SyncApiService,
    private val assetRepositoryImpl: AssetRepositoryImpl,
    private val partCatalogRepositoryImpl: PartCatalogRepositoryImpl,
    private val inventoryRepositoryImpl: InventoryRepositoryImpl,
    private val workOrderRepositoryImpl: WorkOrderRepositoryImpl,
    private val consumedPartRepositoryImpl: ConsumedPartRepositoryImpl
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.i("SyncWorker", "Initialized")
        // First part: push
        return try {
            val dirtyWorkOrders = workOrderRepositoryImpl.getDirtyWorkOrders()
            val dirtyConsumedParts = consumedPartRepositoryImpl.getDirtyConsumedParts()

            if (dirtyWorkOrders.isNotEmpty() || dirtyConsumedParts.isNotEmpty()) {
                val pushRequest = SyncPushRequestDto(dirtyWorkOrders.map { it.toDto() }, dirtyConsumedParts.map { it.toDto() })

                val pushResponse = syncApiService.pushChanges(pushRequest)
                if (pushResponse.isSuccessful && pushResponse.body() != null) {
                    val confirmation = pushResponse.body()!!
                    if (confirmation.syncedWorkOrderIds.isNotEmpty()) {
                        workOrderRepositoryImpl.markAsSynced(confirmation.syncedWorkOrderIds)
                    }
                    if (confirmation.syncedConsumedPartIds.isNotEmpty()) {
                        consumedPartRepositoryImpl.markAsSynced(confirmation.syncedWorkOrderIds)
                    }
                    Log.d("SyncWorker", "Push synchronization successful.")
                }
            } else {
                Log.e("SyncWorker", "Push operation rejected by remote API server.")
                return Result.retry()
            }


            // Second part: pull
            val lastSyncTimestamp: Long = 0

            val pullResponse = syncApiService.pullChanges(lastSyncTimestamp)

            if (pullResponse.isSuccessful && pullResponse.body() != null) {
                val deltas = pullResponse.body()!!

                if (deltas.assets.isNotEmpty()) assetRepositoryImpl.upsertAssets(deltas.assets.map { it.toEntity() })
                if (deltas.partCatalog.isNotEmpty()) partCatalogRepositoryImpl.upsertParts(deltas.partCatalog.map { it.toEntity() })
                if (deltas.inventory.isNotEmpty()) inventoryRepositoryImpl.upsertInventory(deltas.inventory.map { it.toEntity() })
                if (deltas.workOrders.isNotEmpty()) workOrderRepositoryImpl.upsertWorkOrders(deltas.workOrders.map { it.toEntity() })
                if (deltas.consumedParts.isNotEmpty()) consumedPartRepositoryImpl.upsertConsumedParts(deltas.consumedParts.map { it.toEntity() })

                Log.d("SyncWorker", "Pull synchronization delta application successful.")
                return Result.success()
            } else {
                Log.e("SyncWorker", "Pull operation failed to download remote data delta update.")
                return Result.retry()
            }
        } catch (e: Exception) {
            Log.d("SyncWorker", "Starting background sync execution cycle...")
            Result.retry()
        }

    }
}