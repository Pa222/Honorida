package com.honorida.data.external.models

import com.fasterxml.jackson.annotation.JsonProperty

data class CheckUpdateResponse (
    @JsonProperty("updateRequired") val updateRequired: Boolean,
    @JsonProperty("updateUrl") val updateUrl: String?,
    @JsonProperty("latestAppVersion") val latestAppVersion: String?,
    @JsonProperty("releaseUrl") val releaseUrl: String?,
)
