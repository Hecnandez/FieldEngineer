package com.example.fieldengineer.core.data.repository

import android.util.Log
import com.example.fieldengineer.core.data.local.dao.PartCatalogDao
import com.example.fieldengineer.core.data.local.entity.PartCatalogEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartCatalogRepositoryImpl @Inject constructor(
    private val partCatalogDao: PartCatalogDao
) {
    fun getParts(): Flow<List<PartCatalogEntity>> {
        return partCatalogDao.getAllActiveParts().catch { e ->
            Log.e("PartCatalogImpl", "Error reading active catalog parts from Room", e)
            emit(emptyList())
        }
    }

    suspend fun upsertParts(parts: List<PartCatalogEntity>) {
        partCatalogDao.upsertParts(parts)
    }
}