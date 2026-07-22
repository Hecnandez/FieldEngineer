package com.example.fieldengineer.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "model_number") val modelNumber: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "schematic_url") val schematicUrl: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean,
)