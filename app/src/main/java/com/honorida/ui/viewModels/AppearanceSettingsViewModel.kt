package com.honorida.ui.viewModels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.ui.uiStates.AppearanceUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppearanceSettingsViewModel(
    private val appearancePreferencesStore: DataStore<AppearancePreferences>
) : ViewModel() {
    private val _uiState: StateFlow<AppearanceUiState> = appearancePreferencesStore.data.map {
        AppearanceUiState(darkThemePreference = it.darkThemePreference)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppearanceUiState()
    )

    val uiState
        get() = _uiState

    fun updateDarkThemeSetting(value: DarkThemePreference) {
        viewModelScope.launch {
            appearancePreferencesStore.updateData {
                it.copy(darkThemePreference = value)
            }
        }
    }
}