package com.example.fieldengineer.core.data.local.db

import android.content.Context
import androidx.room.Room
import com.example.fieldengineer.core.data.local.dao.AssetDao
import com.example.fieldengineer.core.data.local.dao.ConsumedPartDao
import com.example.fieldengineer.core.data.local.dao.InventoryDao
import com.example.fieldengineer.core.data.local.dao.PartCatalogDao
import com.example.fieldengineer.core.data.local.dao.WorkOrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "field_engineer.db"
        ).build()
    }

    @Provides
    fun provideAssetDao(database: AppDatabase): AssetDao = database.assetDao()

    @Provides
    fun providePartCatalogDao(database: AppDatabase): PartCatalogDao = database.partCatalogDao()

    @Provides
    fun provideConsumedPartDao(database: AppDatabase): ConsumedPartDao = database.consumedPartDao()

    @Provides
    fun provideWorkOrderDao(database: AppDatabase): WorkOrderDao = database.workOrderDao()

    @Provides
    fun provideInventoryDao(database: AppDatabase): InventoryDao = database.inventoryDao()
}