package com.honorida.representation.uiStates

import com.honorida.data.external.models.VersionInfoResponse
import com.honorida.domain.constants.LoadingState

data class AppUpdateUiState(
    val loadingState: LoadingState = LoadingState.Loading,
    val releaseInfo: VersionInfoResponse? = null
)