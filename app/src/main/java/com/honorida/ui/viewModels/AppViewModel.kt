package com.honorida.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.data.local.enums.toDarkThemePreference
import com.honorida.data.local.enums.toInt
import com.honorida.data.local.repositories.DataStoreRepository
import com.honorida.ui.uiStates.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())

    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val darkThemePreference = dataStoreRepository.getFirstPreference(
                DataStoreKey.DARK_THEME_PREFERENCE,
                DarkThemePreference.FOLLOW_SYSTEM.toInt()
            ).toDarkThemePreference() ?: DarkThemePreference.FOLLOW_SYSTEM

            _uiState.update {
                it.copy(
                    darkThemePreference = darkThemePreference,
                )
            }
        }
    }
}