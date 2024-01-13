package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.representation.uiStates.AppUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    protoDataStore: IProtoDataStore
) : ViewModel() {
    private val _uiState: StateFlow<AppUiState> = protoDataStore.appearancePreferences.data.map {
        AppUiState(darkThemePreference = it.darkThemePreference)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppUiState()
    )

    val uiState
        get() = _uiState
}