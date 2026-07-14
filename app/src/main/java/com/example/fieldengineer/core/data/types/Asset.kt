package com.example.fieldengineer.data.types

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Asset (
    val id: String,
    val name: String,
    val modelNumber: String,
    val latitude: Double,
    val longitude: Double,
    val schematicUrl: String,
    val updatedAt: Long,
    val isDeleted: Boolean,
)