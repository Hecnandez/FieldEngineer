package com.example.fieldengineer.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SyncPushResponseDto(
    @SerializedName("synced_work_order_ids") val syncedWorkOrderIds: List<String>,
    @SerializedName("synced_consumed_part_ids") val syncedConsumedPartIds: List<String>
)