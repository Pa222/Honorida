package com.honorida.data.external.services

import com.honorida.data.external.models.CheckUpdateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IHonoridaApiService {
    @GET("api/versions/needs-update")
    suspend fun checkUpdates(
        @Query("versionName") versionName: String,
        @Query("checkPreRelease") checkForPreRelease: Boolean = true
    ) : CheckUpdateResponse
}