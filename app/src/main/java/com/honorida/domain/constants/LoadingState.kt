package com.honorida.domain.constants

sealed class LoadingState {
    data object Idle: LoadingState()
    data object Loading: LoadingState()
    data object Loaded: LoadingState()
    data object Failed: LoadingState()
}