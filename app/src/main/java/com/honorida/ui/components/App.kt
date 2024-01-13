package com.honorida.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.honorida.HonoridaApp
import com.honorida.representation.viewModels.AppViewModel
import com.honorida.representation.viewModels.helpers.viewModelFactory
import com.honorida.ui.components.navigation.NavBar
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.navigation.graphs.buildAppUpdateNavGraph
import com.honorida.ui.components.navigation.graphs.buildHistoryPageNavGraph
import com.honorida.ui.components.navigation.graphs.buildLibraryPageNavGraph
import com.honorida.ui.components.navigation.graphs.buildMorePageNavGraph
import com.honorida.ui.theme.HonoridaTheme

@Composable
fun App(
    viewModel: AppViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = viewModelFactory {
            AppViewModel(
                appearancePreferenceStore =
                HonoridaApp.appModule.protoDataStore.appearancePreferences
            )
        }
    )
) {
    val uiState = viewModel.uiState.collectAsState().value
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
                startDestination = Routes.LIBRARY.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                buildLibraryPageNavGraph()
                buildHistoryPageNavGraph()
                buildMorePageNavGraph(navController)
                buildAppUpdateNavGraph(navController)
            }
        }
    }
}

@Preview
@Composable
private fun AppPreview(){
    App()
}