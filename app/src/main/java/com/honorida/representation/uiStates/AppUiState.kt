package com.honorida.representation.uiStates

import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.data.models.protoStore.AppearancePreferences

data class AppUiState (
    val appearancePreferences: AppearancePreferences = AppearancePreferences(),
)