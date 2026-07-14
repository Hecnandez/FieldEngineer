package com.example.fieldengineer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "part_catalog")
data class PartCatalogEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "part_number") val partNumber: String,
    val name: String,
    val category: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean,
)
