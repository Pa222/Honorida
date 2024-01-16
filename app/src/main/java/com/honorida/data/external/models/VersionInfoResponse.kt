package com.honorida.data.external.models

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class VersionInfoResponse(
    @JsonProperty("downloadUrl") val downloadUrl: String,
    @JsonProperty("gitHubUrl") val gitHubUrl: String,
    @JsonProperty("versionName") val versionName: String,
    @JsonProperty("releaseNotes") val releaseNotes: String,
)