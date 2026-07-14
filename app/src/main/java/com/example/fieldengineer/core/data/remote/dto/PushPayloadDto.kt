package com.example.fieldengineer.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SyncPushRequestDto(
    @SerializedName("work_orders") val workOrders: List<WorkOrderDto>,
    @SerializedName("consumed_parts") val consumedParts: List<ConsumedPartDto>
)

data class WorkOrderDto(
    @SerializedName("id") val id: String,
    @SerializedName("asset_id") val assetId: String,
    @SerializedName("assigned_to") val assignedTo: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("status") val status: String,
    @SerializedName("technician_notes") val technicianNotes: String,
    @SerializedName("updated_at") val updatedAt: Long,
    @SerializedName("is_deleted") val isDeleted: Boolean
)

data class ConsumedPartDto(
    @SerializedName("id") val id: String,
    @SerializedName("work_order_id") val workOrderId: String,
    @SerializedName("part_id") val partId: String,
    @SerializedName("quantity_used") val quantityUsed: Int,
    @SerializedName("updated_at") val updatedAt: Long,
    @SerializedName("is_deleted") val isDeleted: Boolean
)