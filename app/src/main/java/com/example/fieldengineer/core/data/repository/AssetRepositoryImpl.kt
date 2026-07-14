package com.example.fieldengineer.data.repository

import android.util.Log
import com.example.fieldengineer.core.data.local.dao.AssetDao
import com.example.fieldengineer.data.local.entity.AssetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetRepositoryImpl @Inject constructor(
    private val assetDao: AssetDao
) {

    fun getAssets(): Flow<List<AssetEntity>> {
        return assetDao.getAllActiveAssets().catch { e ->
            Log.e("AssetRepositoryImpl", "Error reading active assets from Room", e)
            emit(emptyList())
        }
    }

    suspend fun upsertAssets(assets: List<AssetEntity>) {
        assetDao.upsertAssets(assets)
    }
}