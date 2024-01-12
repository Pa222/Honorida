package com.honorida.activities.main.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.activities.main.ui.uiStates.AppUpdateUiState
import com.honorida.data.external.models.VersionInfoResponse
import com.honorida.data.external.services.IHonoridaApiService
import com.honorida.data.local.interfaces.IDownloader
import com.honorida.domain.constants.LoadingState
import com.honorida.domain.services.AppUpdater
import com.honorida.domain.services.interfaces.IAppUpdater
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppUpdateViewModel(
    private val appUpdater: IAppUpdater,
    private val apiService: IHonoridaApiService,
    private val releaseId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUpdateUiState())

    val uiState
        get() = _uiState.asStateFlow()

    init {
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