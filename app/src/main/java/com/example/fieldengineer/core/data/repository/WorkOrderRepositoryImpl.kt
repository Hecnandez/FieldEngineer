package com.example.fieldengineer.data.repository

import android.util.Log
import com.example.fieldengineer.core.data.local.dao.WorkOrderDao
import com.example.fieldengineer.data.local.entity.WorkOrderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkOrderRepositoryImpl @Inject constructor(
    private val workOrderDao: WorkOrderDao
){
    fun getAllActiveWorkOrders(): Flow<List<WorkOrderEntity>> {
        return workOrderDao.getAllActiveWorkOrders().catch { e->
            Log.e("WorkOrderRepositoryImpl", "Error reading active work orders from Room", e)
            emit(emptyList())
        }
    }

    suspend fun getWorkOrderById(id: String): WorkOrderEntity? {
        return workOrderDao.getWordOrderById(id)
    }

    suspend fun getDirtyWorkOrders(): List<WorkOrderEntity> {
        return workOrderDao.getDirtyWorkOrders()
    }

    suspend fun upsertWorkOrders(workOrders: List<WorkOrderEntity>){
        workOrderDao.upsertWorkOrders(*workOrders.toTypedArray())
    }

    suspend fun softDeleteWorkOrder(id: String, timestamp: Long) {
        workOrderDao.softDeleteWorkOrder(id, timestamp)
    }

    suspend fun markAsSynced(ids: List<String>) {
        workOrderDao.markAsSynced(ids)
    }
}