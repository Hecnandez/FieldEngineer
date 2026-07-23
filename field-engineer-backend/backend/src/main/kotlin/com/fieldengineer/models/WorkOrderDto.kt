package com.fieldengineer.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkOrderDto(
    val id: String,
    @SerialName("asset_id") val assetId: String? = null,
    @SerialName("assigned_to") val assignedTo: String,
    val title: String,
    val description: String? = null,
    val status: String,
    @SerialName("technician_notes") val technicianNotes: String? = null,
    @SerialName("updated_at") val updatedAt: Long,
    @SerialName("is_deleted") val isDeleted: Boolean = false
)

@Serializable
data class SyncPullResponse(
    @SerialName("server_timestamp") val serverTimestamp: Long,
    @SerialName("assets") val assets: List<WorkOrderDto> = emptyList(),
    @SerialName("part_catalog") val partCatalog: List<WorkOrderDto> = emptyList(),
    @SerialName("inventory") val inventory: List<WorkOrderDto> = emptyList(),
    @SerialName("work_orders") val workOrders: List<WorkOrderDto> = emptyList(),
    @SerialName("consumed_parts") val consumedParts: List<WorkOrderDto> = emptyList()
)

@Serializable
data class SyncPushRequest(
    @SerialName("work_orders") val workOrders: List<WorkOrderDto> = emptyList()
)

@Serializable
data class SyncPushResponse(
    @SerialName("synced_work_order_ids") val syncedWorkOrderIds: List<String> = emptyList(),
    @SerialName("synced_consumed_part_ids") val syncedConsumedPartIds: List<String> = emptyList()
)
