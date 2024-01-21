package com.honorida.data.models.protoStore

import kotlinx.serialization.Serializable

@Serializable
data class ReaderPreferences(
    val fontSize: Int = 14
)