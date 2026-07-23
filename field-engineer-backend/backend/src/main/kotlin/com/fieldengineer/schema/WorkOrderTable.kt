package com.fieldengineer.schema

import org.jetbrains.exposed.sql.Table

object WorkOrdersTable : Table("work_orders") {
    val id = varchar("id", 36)
    val assetId = varchar("asset_id", 36).nullable()
    val assignedTo = varchar("assigned_to", 100)
    val title = varchar("title", 255)
    val description = text("description").nullable()
    val status = varchar("status", 50)
    val technicianNotes = text("technician_notes").nullable()
    val updatedAt = long("updated_at")
    val isDeleted = bool("is_deleted").default(false)

    override val primaryKey = PrimaryKey(id)
}
