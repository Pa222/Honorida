package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.representation.uiStates.AppUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    protoDataStore: IProtoDataStore,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())

    private val _appearancePreferences = protoDataStore.appearancePreferences.data
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            AppearancePreferences()
        )

    val uiState = combine(_uiState, _appearancePreferences) {
        uiState, appearancePreferences ->
        uiState.copy(
            appearancePreferences = appearancePreferences,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AppUiState()
    )
}