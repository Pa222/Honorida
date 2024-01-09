package com.honorida.activities.main.ui.uiStates

import com.honorida.data.models.protoStore.ApplicationPreferences
import kotlinx.coroutines.flow.Flow

data class ApplicationPreferencesUiState(
    val preferences: Flow<ApplicationPreferences>
)