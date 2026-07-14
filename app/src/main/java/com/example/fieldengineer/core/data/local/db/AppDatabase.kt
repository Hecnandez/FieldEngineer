package com.example.fieldengineer.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fieldengineer.core.data.local.dao.AssetDao
import com.example.fieldengineer.core.data.local.dao.ConsumedPartDao
import com.example.fieldengineer.core.data.local.dao.InventoryDao
import com.example.fieldengineer.core.data.local.dao.PartCatalogDao
import com.example.fieldengineer.core.data.local.dao.WorkOrderDao
import com.example.fieldengineer.data.local.entity.AssetEntity
import com.example.fieldengineer.data.local.entity.ConsumedPartEntity
import com.example.fieldengineer.data.local.entity.InventoryEntity
import com.example.fieldengineer.data.local.entity.PartCatalogEntity
import com.example.fieldengineer.data.local.entity.WorkOrderEntity

@Database(
    entities = [AssetEntity::class, PartCatalogEntity::class, ConsumedPartEntity::class, WorkOrderEntity::class, InventoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun assetDao(): AssetDao

    abstract fun partCatalogDao(): PartCatalogDao

    abstract fun consumedPartDao(): ConsumedPartDao

    abstract fun workOrderDao(): WorkOrderDao

    abstract fun inventoryDao(): InventoryDao
}