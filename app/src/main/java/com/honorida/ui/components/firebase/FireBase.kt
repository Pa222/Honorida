package com.honorida.ui.components.firebase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.honorida.representation.uiStates.FireBaseUiState
import com.honorida.representation.viewModels.FireBaseViewModel

@Composable
fun FireBase(
    viewModel: FireBaseViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState(initial = FireBaseUiState()).value
    if (uiState.initialized) {
        if (uiState.checkForPreRelease) {
            Firebase.messaging.subscribeToTopic("AppUpdates-PreRelease")
        } else {
            Firebase.messaging.unsubscribeFromTopic("AppUpdates-PreRelease")
        }

        Firebase.messaging.subscribeToTopic("AppUpdates")
    }
}