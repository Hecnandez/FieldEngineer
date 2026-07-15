package com.example.fieldengineer.core.data

import com.example.fieldengineer.core.data.local.entity.WorkOrderEntity
import com.example.fieldengineer.core.data.remote.dto.ConsumedPartDto
import com.example.fieldengineer.core.data.remote.dto.WorkOrderDto
import com.example.fieldengineer.core.data.local.entity.AssetEntity
import com.example.fieldengineer.core.data.local.entity.ConsumedPartEntity
import com.example.fieldengineer.core.data.local.entity.InventoryEntity
import com.example.fieldengineer.core.data.local.entity.PartCatalogEntity
import com.example.fieldengineer.core.data.remote.dto.AssetDto
import com.example.fieldengineer.core.data.remote.dto.InventoryDto
import com.example.fieldengineer.core.data.remote.dto.PartCatalogDto

fun WorkOrderEntity.toDto(): WorkOrderDto {
    return WorkOrderDto(
        id = this.id,
        assetId = this.assetId,
        assignedTo = this.assignedTo,
        title = this.title,
        description = this.description,
        status = this.status,
        technicianNotes = this.technicianNotes,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted
    )
}

fun WorkOrderDto.toEntity(isSynced: Boolean = true): WorkOrderEntity {
    return WorkOrderEntity(
        id = this.id,
        assetId = this.assetId,
        assignedTo = this.assignedTo,
        title = this.title,
        description = this.description,
        status = this.status,
        technicianNotes = this.technicianNotes,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted,
        isSynced = isSynced
    )
}

fun ConsumedPartEntity.toDto(): ConsumedPartDto {
    return ConsumedPartDto(
        id = this.id,
        workOrderId = this.workOrderId,
        partId = this.partId,
        quantityUsed = this.quantityUsed,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted
    )
}

fun ConsumedPartDto.toEntity(isSynced: Boolean = true): ConsumedPartEntity {
    return ConsumedPartEntity(
        id = this.id,
        workOrderId = this.workOrderId,
        partId = this.partId,
        quantityUsed = this.quantityUsed,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted,
        isSynced = isSynced
    )
}

fun AssetDto.toEntity(): AssetEntity {
    return AssetEntity(
        id = this.id,
        name = this.name,
        modelNumber = this.modelNumber,
        latitude = this.latitude,
        longitude = this.longitude,
        schematicUrl = this.schematicUrl,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted
    )
}

fun PartCatalogDto.toEntity(): PartCatalogEntity {
    return PartCatalogEntity(
        id = this.id,
        partNumber = this.partNumber,
        name = this.name,
        category = this.category,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted
    )
}

fun InventoryDto.toEntity(): InventoryEntity {
    return InventoryEntity(
        partId = this.partId,
        quantityAvailable = this.quantityAvailable,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted
    )
}