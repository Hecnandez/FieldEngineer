package com.example.fieldengineer.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fieldengineer.data.local.entity.ConsumedPartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumedPartDao {

    @Query("""
        SELECT c.*, p.name, p.part_number 
        FROM consumed_parts c
        INNER JOIN part_catalog p ON c.part_id = p.id
        WHERE c.work_order_id = :workOrderId AND c.is_deleted = 0
    """)
    fun getConsumedPartsForOrder(workOrderId: String): Flow<List<ConsumedPartEntity>>

    @Query("SELECT * FROM consumed_parts WHERE is_synced = 0")
    suspend fun getDirtyConsumedParts(): List<ConsumedPartEntity>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConsumedParts(vararg consumedParts: ConsumedPartEntity)

    @Query("UPDATE consumed_parts SET is_synced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<String>)

    @Query("UPDATE consumed_parts SET is_deleted = 1, is_synced = 0, updated_at = :timestamp WHERE id = :id")
    suspend fun softDeleteConsumedPart(id: String, timestamp: Long)
}