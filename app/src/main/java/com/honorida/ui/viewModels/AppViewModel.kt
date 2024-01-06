package com.honorida.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.data.local.enums.UserPreferencesKey
import com.honorida.data.local.enums.toInt
import com.honorida.data.local.repositories.UserPreferencesRepository
import com.honorida.ui.uiStates.AppUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<AppUiState> =
        userPreferencesRepository.darkThemePreference.map {
            AppUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppUiState()
        )

    fun updateDarkThemePreference(preference: DarkThemePreference) {
        viewModelScope.launch {
            userPreferencesRepository.putPreference(
                UserPreferencesKey.DARK_THEME_PREFERENCE, preference.toInt()
            )
        }
    }
}