package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.external.models.VersionInfoResponse
import com.honorida.data.external.services.IHonoridaApiService
import com.honorida.domain.constants.LoadingState
import com.honorida.domain.services.interfaces.IAppUpdater
import com.honorida.representation.uiStates.AppUpdateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppUpdateViewModel @Inject constructor(
    private val appUpdater: IAppUpdater,
    private val apiService: IHonoridaApiService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUpdateUiState())

    val uiState: StateFlow<AppUpdateUiState>
        get() {
            return _uiState.asStateFlow()
        }

    fun loadReleaseInfo(releaseId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        loadingState = LoadingState.Loading,
                    )
                }
                val response = apiService.getReleaseInfo(releaseId)
                _uiState.update {
                    it.copy(
                        loadingState = LoadingState.Loaded,
                        releaseInfo = response
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(loadingState = LoadingState.Failed)
                }
            }
        }
    }

    fun startUpdate(versionInfo: VersionInfoResponse) {
        appUpdater.startUpdate(versionInfo)
    }
}