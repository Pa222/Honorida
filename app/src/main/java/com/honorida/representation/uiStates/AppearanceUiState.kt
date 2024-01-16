package com.honorida.representation.uiStates

import com.honorida.data.local.enums.DarkThemePreference

data class AppearanceUiState(
    val darkThemePreference: DarkThemePreference = DarkThemePreference.FOLLOW_SYSTEM
)