package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.data.models.protoStore.ReaderPreferences
import com.honorida.representation.uiStates.ReaderSettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderSettingsViewModel @Inject constructor(
    private val dataStore: IProtoDataStore
): ViewModel() {
    private val _uiState = MutableStateFlow(ReaderSettingsUiState())

    private val _readerPreferences = dataStore.readerPreferences.data.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ReaderPreferences()
    )

    val uiState = combine(_uiState, _readerPreferences) {
        uiState, readerPreferences ->
        uiState.copy(
            readerSettings = readerPreferences
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ReaderSettingsUiState()
    )

    fun updateFontSize(value: Int) {
        viewModelScope.launch {
            dataStore.readerPreferences.updateData {
                it.copy(fontSize = value)
            }
        }
    }
}