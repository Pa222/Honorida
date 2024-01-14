package com.honorida.representation.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.ui.components.navigation.Routes
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val preferencesRepository: IDataStoreRepository
): ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Routes.LIBRARY.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            preferencesRepository.getPreference(DataStoreKey.StorageConfigured, false)
                .collect { completed ->
                    if (completed) {
                        _startDestination.value = Routes.LIBRARY.route
                    } else {
                        _startDestination.value = Routes.STORAGE_SETUP.route
                    }
                    _isLoading.value = false
                }
        }
    }
}