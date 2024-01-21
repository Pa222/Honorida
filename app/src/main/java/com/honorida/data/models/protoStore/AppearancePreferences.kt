package com.honorida.data.models.protoStore

import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.ui.theme.ThemeType
import kotlinx.serialization.Serializable

@Serializable
data class AppearancePreferences(
    val darkThemePreference: DarkThemePreference = DarkThemePreference.FOLLOW_SYSTEM,
    val currentTheme: ThemeType = ThemeType.DEFAULT
)