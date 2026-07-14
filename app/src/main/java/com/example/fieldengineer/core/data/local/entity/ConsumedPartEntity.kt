package com.example.fieldengineer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consumed_parts")
data class ConsumedPartEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "work_order_id") val workOrderId: String,
    @ColumnInfo(name = "part_id") val partId: String,
    @ColumnInfo(name = "quantity_used") val quantityUsed: Int,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean,
    @ColumnInfo(name = "is_synced") val isSynced: Boolean
)
