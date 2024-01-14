package com.honorida.representation.uiStates

import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.ui.components.navigation.Routes

data class AppUiState (
    val darkThemePreference: DarkThemePreference = DarkThemePreference.FOLLOW_SYSTEM,
)