package com.honorida.activities.main.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.honorida.HonoridaApp
import com.honorida.activities.main.ui.components.appUpdate.AppUpdatePage
import com.honorida.activities.main.ui.components.navigation.DeepLinks
import com.honorida.activities.main.ui.components.navigation.NavBar
import com.honorida.activities.main.ui.components.navigation.Routes
import com.honorida.activities.main.ui.components.navigation.graphs.buildAppUpdateNavGraph
import com.honorida.activities.main.ui.components.navigation.graphs.buildHistoryPageNavGraph
import com.honorida.activities.main.ui.components.navigation.graphs.buildLibraryPageNavGraph
import com.honorida.activities.main.ui.components.navigation.graphs.buildMorePageNavGraph
import com.honorida.activities.main.ui.theme.HonoridaTheme
import com.honorida.activities.main.ui.viewModels.AppViewModel
import com.honorida.activities.main.ui.viewModels.helpers.viewModelFactory
import com.honorida.domain.constants.Extras

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