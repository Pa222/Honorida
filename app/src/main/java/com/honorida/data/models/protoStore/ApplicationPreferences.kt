package com.honorida.data.models.protoStore

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationPreferences(
    val checkUpdatesOnStartUp: Boolean = false
)