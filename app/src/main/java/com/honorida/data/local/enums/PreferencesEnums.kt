package com.honorida.data.local.enums

enum class DarkThemePreference {
    ENABLED,
    DISABLED,
    FOLLOW_SYSTEM
}

fun DarkThemePreference.toInt(): Int {
    return when (this) {
        DarkThemePreference.ENABLED -> 0
        DarkThemePreference.DISABLED -> 1
        DarkThemePreference.FOLLOW_SYSTEM -> 2
    }
}

fun Int.toDarkThemePreference(): DarkThemePreference? {
    return when (this) {
        0 -> DarkThemePreference.ENABLED
        1 -> DarkThemePreference.DISABLED
        2 -> DarkThemePreference.FOLLOW_SYSTEM
        else -> null
    }
}

