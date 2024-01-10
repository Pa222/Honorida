package com.honorida.domain.constants

sealed class Extras(
    val key: String
) {
    data object UpdateRequired: Extras("updateRequired")
    data object UpdateUrl: Extras("updateUrl")
    data object LatestAppVersion: Extras("latestAppVersion")
    data object ReleaseUrl: Extras("releaseUrl")
}