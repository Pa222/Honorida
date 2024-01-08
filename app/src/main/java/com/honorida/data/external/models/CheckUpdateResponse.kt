package com.honorida.data.external.models

import android.os.Bundle
import com.fasterxml.jackson.annotation.JsonProperty
import com.honorida.domain.services.Extras

data class CheckUpdateResponse (
    @JsonProperty("updateRequired") val updateRequired: Boolean,
    @JsonProperty("updateUrl") val updateUrl: String?,
    @JsonProperty("latestAppVersion") val latestAppVersion: String?,
    @JsonProperty("releaseUrl") val releaseUrl: String?,
) {

    fun toExtras() : Bundle {
        val extras = Bundle()
        extras.putString(Extras.UpdateUrl.key, updateUrl)
        extras.putString(Extras.LatestAppVersion.key, latestAppVersion)
        extras.putString(Extras.ReleaseUrl.key, releaseUrl)
        return extras
    }

    companion object {
        fun fromExtras(extras: Bundle) : CheckUpdateResponse {
            return CheckUpdateResponse(
                updateRequired = extras.getBoolean(Extras.UpdateRequired.key, false),
                updateUrl = extras.getString(Extras.UpdateUrl.key, ""),
                latestAppVersion = extras.getString(Extras.LatestAppVersion.key, ""),
                releaseUrl = extras.getString(Extras.ReleaseUrl.key, "")
            )
        }
    }
}
