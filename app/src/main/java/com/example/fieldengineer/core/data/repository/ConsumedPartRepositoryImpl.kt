package com.example.fieldengineer.data.repository

import android.util.Log
import com.example.fieldengineer.core.data.local.dao.ConsumedPartDao
import com.example.fieldengineer.data.local.entity.ConsumedPartEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConsumedPartRepositoryImpl @Inject constructor(
    private val consumedPartDao: ConsumedPartDao
){
    fun getConsumedPartsForOrder(workOrderId: String): Flow<List<ConsumedPartEntity>>{
        return consumedPartDao.getConsumedPartsForOrder(workOrderId).catch { e->
            Log.e("ConsumedPartsRepositoryImpl", "Error reading consumed parts for order ${workOrderId} from Room", e)
            emit(emptyList())
        }
    }

    suspend fun getDirtyConsumedParts(): List<ConsumedPartEntity> {
        return consumedPartDao.getDirtyConsumedParts()
    }

    suspend fun upsertConsumedParts(consumedParts: List<ConsumedPartEntity>) {
        consumedPartDao.upsertConsumedParts(*consumedParts.toTypedArray())
    }

    suspend fun markAsSynced(ids: List<String>) {
        consumedPartDao.markAsSynced(ids)
    }

    suspend fun softDeleteConsumedPart(id: String, timestamp: Long) {
        consumedPartDao.softDeleteConsumedPart(id, timestamp)
    }
}