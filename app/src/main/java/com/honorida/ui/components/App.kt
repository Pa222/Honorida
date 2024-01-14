package com.honorida.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.android.datatransport.runtime.Destination
import com.honorida.representation.viewModels.AppViewModel
import com.honorida.ui.components.navigation.NavBar
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.navigation.graphs.buildAppUpdateNavGraph
import com.honorida.ui.components.navigation.graphs.buildHistoryPageNavGraph
import com.honorida.ui.components.navigation.graphs.buildLibraryPageNavGraph
import com.honorida.ui.components.navigation.graphs.buildMorePageNavGraph
import com.honorida.ui.components.navigation.graphs.buildStorageSetAppNavGraph
import com.honorida.ui.theme.HonoridaTheme

@Composable
fun App(
    startDestination: String,
    viewModel: AppViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val darkThemePreference = uiState.darkThemePreference
    val navController = rememberNavController()

    HonoridaTheme(
        darkThemePreference = darkThemePreference
    ) {
        Scaffold(
            bottomBar = {
                NavBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                buildLibraryPageNavGraph()
                buildHistoryPageNavGraph()
                buildMorePageNavGraph(navController)
                buildAppUpdateNavGraph(navController)
                buildStorageSetAppNavGraph(navController)
            }
        }
    }
}