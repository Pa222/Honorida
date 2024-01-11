package com.honorida.activities.main.ui.uiStates

sealed interface CheckForUpdatesUiState {
    data object Loading : CheckForUpdatesUiState
    data object Pending : CheckForUpdatesUiState
}