package com.example.fieldengineer.features.dashboard

import com.example.fieldengineer.core.data.local.entity.AssetEntity
import com.example.fieldengineer.core.data.local.entity.ConsumedPartEntity
import com.example.fieldengineer.core.data.local.entity.InventoryEntity
import com.example.fieldengineer.core.data.local.entity.PartCatalogEntity
import com.example.fieldengineer.core.data.local.entity.WorkOrderEntity

val mockAssets = listOf(
    AssetEntity(
        id = "ASSET-101",
        name = "Carrier Central AC Unit",
        modelNumber = "38CKC036",
        latitude = 34.0522,
        longitude = -118.2437,
        schematicUrl = "https://example.com/schematics/ac-101.pdf",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    ),
    AssetEntity(
        id = "ASSET-205",
        name = "Otis Service Elevator",
        modelNumber = "GEN2-S",
        latitude = 34.0525,
        longitude = -118.2440,
        schematicUrl = "https://example.com/schematics/elevator-205.pdf",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    ),
    AssetEntity(
        id = "ASSET-099",
        name = "Cummins Backup Generator",
        modelNumber = "C150D5D",
        latitude = 34.0510,
        longitude = -118.2430,
        schematicUrl = "https://example.com/schematics/gen-099.pdf",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    ),
    AssetEntity(
        id = "ASSET-550",
        name = "Data Center Cooling Rack",
        modelNumber = "RACK-COOL-V4",
        latitude = 34.0530,
        longitude = -118.2450,
        schematicUrl = "https://example.com/schematics/rack-550.pdf",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    )
)
val mockWorkOrders = listOf(
    WorkOrderEntity(
        id = "WO-001",
        assetId = "ASSET-101",
        assignedTo = "tech-01",
        title = "AC Unit Maintenance",
        description = "Routine checkup and filter replacement for the main server room AC.",
        status = "In Progress",
        technicianNotes = "Filters cleaned, checking coolant levels next.",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false,
        isSynced = true
    ),
    WorkOrderEntity(
        id = "WO-002",
        assetId = "ASSET-205",
        assignedTo = "tech-01",
        title = "Elevator Inspection",
        description = "Safety inspection for the service elevator in Sector B.",
        status = "Pending",
        technicianNotes = "",
        updatedAt = System.currentTimeMillis() - 86400000,
        isDeleted = false,
        isSynced = true
    ),
    WorkOrderEntity(
        id = "WO-003",
        assetId = "ASSET-099",
        assignedTo = "tech-01",
        title = "Generator Repair",
        description = "Emergency repair for backup generator failure.",
        status = "Completed",
        technicianNotes = "Replaced faulty spark plug and tested successfully.",
        updatedAt = System.currentTimeMillis() - 43200000,
        isDeleted = false,
        isSynced = false
    ),
    WorkOrderEntity(
        id = "WO-004",
        assetId = "ASSET-550",
        assignedTo = "tech-01",
        title = "Network Rack Cooling",
        description = "Install additional fans in rack 4.",
        status = "Assigned",
        technicianNotes = "Waiting for parts delivery.",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false,
        isSynced = true
    )
)
val mockPartCatalog = listOf(
    PartCatalogEntity(
        id = "PART-001",
        partNumber = "FLT-99",
        name = "HEPA Air Filter",
        category = "HVAC",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    ),
    PartCatalogEntity(
        id = "PART-002",
        partNumber = "SPK-44",
        name = "Iridium Spark Plug",
        category = "Engine",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    ),
    PartCatalogEntity(
        id = "PART-003",
        partNumber = "FUSE-10A",
        name = "10 Amp Blade Fuse",
        category = "Electrical",
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    )
)
val mockInventory = listOf(
    InventoryEntity(
        partId = "PART-001",
        quantityAvailable = 15,
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    ),
    InventoryEntity(
        partId = "PART-002",
        quantityAvailable = 4,
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    ),
    InventoryEntity(
        partId = "PART-003",
        quantityAvailable = 50,
        updatedAt = System.currentTimeMillis(),
        isDeleted = false
    )
)
val mockConsumedParts = listOf(
    // Parts used for the Generator Repair (WO-003)
    ConsumedPartEntity(
        id = "CONS-001",
        workOrderId = "WO-003",
        partId = "PART-002", // Spark Plug
        quantityUsed = 1,
        updatedAt = System.currentTimeMillis(),
        isDeleted = false,
        isSynced = false
    ),
    ConsumedPartEntity(
        id = "CONS-002",
        workOrderId = "WO-003",
        partId = "PART-003", // Fuse
        quantityUsed = 2,
        updatedAt = System.currentTimeMillis(),
        isDeleted = false,
        isSynced = false
    )
)