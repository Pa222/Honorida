package com.honorida.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.honorida.HonoridaApplication
import com.honorida.R
import com.honorida.ui.components.navigation.NavBar
import com.honorida.ui.components.navigation.NavTab
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.pages.appUpdate.AppUpdatePage
import com.honorida.ui.components.pages.history.HistoryPage
import com.honorida.ui.components.pages.library.LibraryPage
import com.honorida.ui.components.pages.more.MorePage
import com.honorida.ui.theme.HonoridaTheme
import com.honorida.ui.viewModels.AppViewModel
import com.honorida.ui.viewModels.helpers.viewModelFactory

@Composable
fun App(
    appViewModel: AppViewModel = viewModel(
        factory = viewModelFactory {
            AppViewModel(HonoridaApplication.appModule.userPreferencesRepository)
        }
    )
) {
    val navController = rememberNavController()

    val navBarItems = listOf(
        NavTab(stringResource(R.string.library), Routes.LIBRARY.route, Icons.Filled.Home,
            Icons.Filled.Home),
        NavTab(stringResource(R.string.history), Routes.HISTORY.route, Icons.Filled.History,
            Icons.Filled.History),
        NavTab(stringResource(R.string.more), Routes.MORE.route, Icons.Filled.MoreHoriz,
            Icons.Filled.MoreHoriz)
    )

    HonoridaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Scaffold(
                bottomBar = {
                    NavBar(navBarItems, navController)
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Routes.LIBRARY.route,
                    enterTransition = {
                        EnterTransition.None
                    },
                    exitTransition = {
                        ExitTransition.None
                    }
                ) {
                    composable(Routes.LIBRARY.route) {
                        LibraryPage(Modifier.padding(innerPadding))
                    }
                    composable(Routes.HISTORY.route) {
                        HistoryPage(Modifier.padding(innerPadding))
                    }
                    composable(Routes.MORE.route) {
                        MorePage(Modifier.padding(innerPadding))
                    }
                    composable(Routes.APP_UPDATE.route) {
                        AppUpdatePage(navController, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppPreview(){
    App()
}