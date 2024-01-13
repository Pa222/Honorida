package com.honorida.representation.uiStates

import com.honorida.data.models.protoStore.UpdatesPreferences
import kotlinx.coroutines.flow.Flow

data class ApplicationPreferencesUiState(
    val updatesPreferences: Flow<UpdatesPreferences>
)