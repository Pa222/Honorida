package com.honorida.ui.uiStates

import com.honorida.data.local.enums.DarkThemePreference

data class AppearanceUiState(
    val darkThemePreference: DarkThemePreference = DarkThemePreference.FOLLOW_SYSTEM
)