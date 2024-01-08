package com.honorida.ui.viewModels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.ui.uiStates.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    appearancePreferenceStore: DataStore<AppearancePreferences>
) : ViewModel() {
    val uiState = MutableStateFlow(AppUiState())

    init {
        viewModelScope.launch {
            val darkThemePreference = appearancePreferenceStore.data.first().darkThemePreference
            uiState.update {
                it.copy(darkThemePreference = darkThemePreference)
            }
        }
    }

    val appearancePreferences = appearancePreferenceStore.data
}