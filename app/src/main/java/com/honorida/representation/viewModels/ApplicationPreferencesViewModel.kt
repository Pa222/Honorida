package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.representation.uiStates.ApplicationPreferencesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApplicationPreferencesViewModel(
    private val preferencesStore: IProtoDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ApplicationPreferencesUiState(
        preferencesStore.updatesPreferences.data
    )
    )

    val uiState
        get() = _uiState.asStateFlow()

    fun updateReceiveAppUpdatesPreference(value: Boolean) {
        viewModelScope.launch {
            preferencesStore.updatesPreferences.updateData {
                it.copy(
                    receiveAppUpdates = value,
                    checkUpdatesOnStartUp = if (value) it.checkUpdatesOnStartUp else false
                )
            }
        }
    }

    fun updateCheckUpdatesOnStartUpPreference(value: Boolean) {
        viewModelScope.launch {
            preferencesStore.updatesPreferences.updateData {
                it.copy(checkUpdatesOnStartUp = value)
            }
        }
    }
}