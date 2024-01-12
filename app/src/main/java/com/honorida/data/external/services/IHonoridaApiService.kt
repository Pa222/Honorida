package com.honorida.data.external.services

import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.data.external.models.VersionInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IHonoridaApiService {
    @GET("api/v2/versions/needs-update")
    suspend fun checkUpdates(
        @Query("versionName") versionName: String,
        @Query("checkPreRelease") checkForPreRelease: Boolean = true
    ) : CheckUpdateResponse

    @GET("api/versions/release-info")
    suspend fun getReleaseInfo(
        @Query("releaseId") releaseId: Int
    ) : VersionInfoResponse
}