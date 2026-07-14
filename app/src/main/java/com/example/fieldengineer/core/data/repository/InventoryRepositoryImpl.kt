package com.example.fieldengineer.core.data.repository

import android.util.Log
import com.example.fieldengineer.core.data.local.dao.InventoryDao
import com.example.fieldengineer.data.local.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepositoryImpl @Inject constructor(
    private val inventoryDao: InventoryDao
) {
    fun getTruckInventory(): Flow<List<InventoryEntity>> {
        return inventoryDao.getTruckInventory().catch { e ->
            Log.e("InventoryRepositoryImpl", "Error reading truck inventory from Room", e)
            emit(emptyList())
        }
    }

    suspend fun getInventoryItem(partId: String): InventoryEntity? {
        return inventoryDao.getInventoryItem(partId)
    }

    suspend fun upsertInventory(inventory: List<InventoryEntity>) {
        inventoryDao.upsertInventory(inventory)
    }

    suspend fun updateLocalInventory(partId: String, newQuantity: Int, timestamp: Long){
        inventoryDao.updateLocalInventory(partId, newQuantity, timestamp)
    }
}