package com.example.fieldengineer.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fieldengineer.core.data.local.entity.WorkOrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkOrderDao {
    @Query("SELECT * FROM work_orders WHERE is_deleted = 0")
    fun getAllActiveWorkOrders(): Flow<List<WorkOrderEntity>>

    @Query("SELECT * FROM work_orders WHERE id = :id")
    suspend fun getWordOrderById(id: String): WorkOrderEntity?

    @Query("SELECT * FROM work_orders WHERE is_synced = 0")
    suspend fun getDirtyWorkOrders(): List<WorkOrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWorkOrders(vararg workOrders: WorkOrderEntity)

    @Query("UPDATE work_orders SET is_deleted = 1, is_synced = 0, updated_at = :timestamp WHERE id = :id")
    suspend fun softDeleteWorkOrder(id: String, timestamp: Long)

    @Query("UPDATE work_orders SET is_synced = 1 WHERE ID IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)
}