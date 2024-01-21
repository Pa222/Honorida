package com.honorida.representation.uiStates

import com.honorida.data.models.protoStore.ReaderPreferences

data class ReaderSettingsUiState(
    val readerSettings: ReaderPreferences = ReaderPreferences()
)
