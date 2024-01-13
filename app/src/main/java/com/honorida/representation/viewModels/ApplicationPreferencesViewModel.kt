package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.data.models.protoStore.UpdatesPreferences
import com.honorida.representation.uiStates.ApplicationPreferencesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationPreferencesViewModel @Inject constructor(
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
                if (!value) {
                    UpdatesPreferences()
                }
                else {
                    it.copy(
                        receiveAppUpdates = true,
                    )
                }
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

    fun updateReceivePreReleaseAppVersionsPreference(value: Boolean) {
        viewModelScope.launch {
            preferencesStore.updatesPreferences.updateData {
                it.copy(receivePreReleaseVersions = value)
            }
        }
    }
}