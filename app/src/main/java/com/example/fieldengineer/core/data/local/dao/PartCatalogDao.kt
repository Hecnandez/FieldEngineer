package com.example.fieldengineer.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fieldengineer.data.local.entity.PartCatalogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartCatalogDao {
    @Query("SELECT * FROM part_catalog WHERE is_deleted = 0")
    fun getAllActiveParts(): Flow<List<PartCatalogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertParts(parts: List<PartCatalogEntity>)
}