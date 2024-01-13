package com.honorida.representation.viewModels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.representation.uiStates.AppUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    appearancePreferenceStore: DataStore<AppearancePreferences>
) : ViewModel() {
    private val _uiState: StateFlow<AppUiState> = appearancePreferenceStore.data.map {
        AppUiState(darkThemePreference = it.darkThemePreference)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppUiState()
    )

    val uiState
        get() = _uiState
}