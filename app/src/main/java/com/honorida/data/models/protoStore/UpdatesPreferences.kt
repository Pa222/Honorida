package com.honorida.data.models.protoStore

import kotlinx.serialization.Serializable

@Serializable
data class UpdatesPreferences(
    val checkUpdatesOnStartUp: Boolean = false,
    val receiveAppUpdates: Boolean = false,
)