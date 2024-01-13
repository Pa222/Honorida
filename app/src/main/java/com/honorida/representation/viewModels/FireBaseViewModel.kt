package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.representation.uiStates.FireBaseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FireBaseViewModel @Inject constructor(
    protoDataStore: IProtoDataStore
): ViewModel() {

    private val _uiState: StateFlow<FireBaseUiState> = protoDataStore.updatesPreferences.data.map {
        FireBaseUiState(
            checkForPreRelease = it.receivePreReleaseVersions,
            initialized = true
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FireBaseUiState()
    )

    val uiState: Flow<FireBaseUiState>
        get() {
            return _uiState
        }
}