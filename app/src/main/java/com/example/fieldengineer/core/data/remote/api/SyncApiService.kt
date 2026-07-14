package com.example.fieldengineer.core.data.remote.api

import com.example.fieldengineer.data.remote.dto.SyncPullResponseDto
import com.example.fieldengineer.core.data.remote.dto.SyncPushRequestDto
import com.example.fieldengineer.data.remote.dto.SyncPushResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SyncApiService {
    @POST("api/v1/sync/push")
    suspend fun pushChanges(
        @Body request: SyncPushRequestDto
    ): Response<SyncPushResponseDto>

    @GET("api/v1/sync/pull")
    suspend fun pullChanges(
        @Query("last_sync") lastSyncTimestamp: Long
    ): Response<SyncPullResponseDto>
}