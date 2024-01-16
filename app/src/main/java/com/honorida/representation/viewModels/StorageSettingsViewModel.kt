package com.honorida.representation.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.representation.uiStates.StorageSettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageSettingsViewModel @Inject constructor(
    private val preferencesStore: IDataStoreRepository,
): ViewModel() {

    private val _selectedStorage = MutableStateFlow("")

    private val _uiState = MutableStateFlow(StorageSettingsUiState())

    val uiState = combine(_uiState, _selectedStorage) { uiState, selectedStorage ->
        uiState.copy(
            selectedStorage = selectedStorage
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        StorageSettingsUiState()
    )

    init {
        viewModelScope.launch {
            preferencesStore.getPreference(DataStoreKey.StorageUri, "").collect {
                storageUri ->
                _selectedStorage.update {
                    storageUri
                }
            }
        }
    }

    fun updateSelectedStorage(uri: Uri) {
        viewModelScope.launch {
            preferencesStore.putPreference(DataStoreKey.StorageUri, uri.toString())
        }
    }
}