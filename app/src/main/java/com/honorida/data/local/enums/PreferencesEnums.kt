package com.honorida.data.local.enums

import android.content.Context
import com.honorida.R
import com.honorida.activities.main.ui.components.shared.controls.selectControl.models.SelectControlValue

enum class DarkThemePreference {
    ENABLED,
    DISABLED,
    FOLLOW_SYSTEM;

    companion object {
        fun getSelectControlOptions(context: Context): List<SelectControlValue<DarkThemePreference>> {
            return listOf(
                SelectControlValue(
                    title = context.getString(R.string.follow_system),
                    value = FOLLOW_SYSTEM
                ),
                SelectControlValue(
                    title = context.getString(R.string.off),
                    value = DISABLED
                ),
                SelectControlValue(
                    title = context.getString(R.string.on),
                    value = ENABLED
                )
            )
        }
    }
}

fun DarkThemePreference.toSelectControlValue(context: Context) :
        SelectControlValue<DarkThemePreference> {
    return when (this) {
        DarkThemePreference.FOLLOW_SYSTEM -> SelectControlValue(
            title = context.getString(R.string.follow_system),
            value = DarkThemePreference.FOLLOW_SYSTEM
        )
        DarkThemePreference.DISABLED -> SelectControlValue(
            title = context.getString(R.string.off),
            value = DarkThemePreference.DISABLED
        )
        DarkThemePreference.ENABLED -> SelectControlValue(
            title = context.getString(R.string.on),
            value = DarkThemePreference.ENABLED
        )
    }
}
