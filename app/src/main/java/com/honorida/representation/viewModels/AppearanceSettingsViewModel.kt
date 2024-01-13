package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.representation.uiStates.AppearanceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppearanceSettingsViewModel @Inject constructor(
    private val preferencesStore: IProtoDataStore
) : ViewModel() {

    private val _uiState: StateFlow<AppearanceUiState> = preferencesStore.appearancePreferences.data.map {
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
            preferencesStore.appearancePreferences.updateData {
                it.copy(darkThemePreference = value)
            }
        }
    }
}