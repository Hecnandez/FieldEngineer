package com.example.fieldengineer.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fieldengineer.core.data.local.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory WHERE is_deleted = 0")
    fun getTruckInventory(): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM inventory WHERE part_ID = :partId LIMIT 1")
    suspend fun getInventoryItem(partId: String): InventoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertInventory(inventory: List<InventoryEntity>)

    @Query("""
        UPDATE inventory
        SET quantity_available = :newQuantity, updated_at = :timestamp
        WHERE part_id = :partId
    """)
    suspend fun updateLocalInventory(partId: String, newQuantity: Int, timestamp: Long)
}