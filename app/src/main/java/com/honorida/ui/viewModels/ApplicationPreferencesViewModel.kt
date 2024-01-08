package com.honorida.ui.viewModels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.models.protoStore.ApplicationPreferences
import com.honorida.ui.uiStates.ApplicationPreferencesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApplicationPreferencesViewModel(
    private val preferencesStore: DataStore<ApplicationPreferences>
) : ViewModel() {

    private val _uiState = MutableStateFlow(ApplicationPreferencesUiState(
        preferencesStore.data
    ))

    val uiState
        get() = _uiState.asStateFlow()

    fun updateCheckForUpdatesOnStartUp(value: Boolean) {
        // TODO: Ask for notification permissions
        viewModelScope.launch {
            preferencesStore.updateData { it.copy(
                checkUpdatesOnStartUp = value
            ) }
        }
    }
}