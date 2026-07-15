package com.example.fieldengineer.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SyncPullResponseDto(
    @SerializedName("server_timestamp") val serverTimestamp: Long, // The new marker to store locally
    @SerializedName("assets") val assets: List<AssetDto>,
    @SerializedName("part_catalog") val partCatalog: List<PartCatalogDto>,
    @SerializedName("inventory") val inventory: List<InventoryDto>,
    @SerializedName("work_orders") val workOrders: List<WorkOrderDto>,
    @SerializedName("consumed_parts") val consumedParts: List<ConsumedPartDto>
)

data class AssetDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("model_number") val modelNumber: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("schematic_url") val schematicUrl: String,
    @SerializedName("updated_at") val updatedAt: Long,
    @SerializedName("is_deleted") val isDeleted: Boolean
)

data class PartCatalogDto(
    @SerializedName("id") val id: String,
    @SerializedName("part_number") val partNumber: String,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("updated_at") val updatedAt: Long,
    @SerializedName("is_deleted") val isDeleted: Boolean
)

data class InventoryDto(
    @SerializedName("part_id") val partId: String,
    @SerializedName("quantity_available") val quantityAvailable: Int,
    @SerializedName("updated_at") val updatedAt: Long,
    @SerializedName("is_deleted") val isDeleted: Boolean
)