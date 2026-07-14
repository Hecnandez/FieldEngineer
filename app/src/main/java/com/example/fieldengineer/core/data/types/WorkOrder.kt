package com.example.fieldengineer.data.types

data class WorkOrder(
    val id: String,
    val assetId: String,
    val assignedTo: String,
    val title: String,
    val description: String,
    val status: String,
    val technicianNotes: String,
    val updatedAt: Long,
    val isDeleted: Boolean,
    val isSynced: Boolean
)
