package com.honorida.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.honorida.HonoridaApp
import com.honorida.R
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.ui.components.navigation.NavBar
import com.honorida.ui.components.navigation.NavTab
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.pages.appUpdate.AppUpdatePage
import com.honorida.ui.components.pages.history.HistoryPage
import com.honorida.ui.components.pages.library.LibraryPage
import com.honorida.ui.components.pages.more.MorePage
import com.honorida.ui.components.pages.more.subPages.about.AboutPage
import com.honorida.ui.components.pages.more.subPages.appSettings.AppSettingsPage
import com.honorida.ui.components.pages.more.subPages.appSettings.subPages.AppearanceSettingsPage
import com.honorida.ui.components.pages.more.subPages.appSettings.subPages.ApplicationPreferencesPage
import com.honorida.ui.theme.HonoridaTheme
import com.honorida.ui.viewModels.AppViewModel
import com.honorida.ui.viewModels.helpers.viewModelFactory

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
    val preferences = viewModel.appearancePreferences
        .collectAsState(initial = AppearancePreferences()).value
    val navController = rememberNavController()

    val navBarItems = listOf(
        NavTab(stringResource(R.string.library), Routes.LIBRARY.route, Icons.Filled.Home,
            Icons.Outlined.Home),
        NavTab(stringResource(R.string.history), Routes.HISTORY.route, Icons.Filled.History,
            Icons.Outlined.History),
        NavTab(stringResource(R.string.more), Routes.MORE_MAIN.route, Icons.Filled.MoreHoriz,
            Icons.Outlined.MoreHoriz)
    )

    HonoridaTheme(
        darkThemePreference = preferences.darkThemePreference
    ) {
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
                ) {
                    composable(Routes.LIBRARY.route) {
                        LibraryPage(Modifier.padding(innerPadding))
                    }
                    composable(Routes.HISTORY.route) {
                        HistoryPage(Modifier.padding(innerPadding))
                    }
                    navigation(
                        route = Routes.MORE.route,
                        startDestination = Routes.MORE_MAIN.route,
                    ) {
                        composable(Routes.MORE_MAIN.route) {
                            MorePage(navController = navController, Modifier.padding(innerPadding))
                        }
                        composable(Routes.MORE_MAIN_ABOUT.route) {
                            AboutPage(navController = navController, Modifier.padding(innerPadding))
                        }
                        composable(Routes.MORE_MAIN_SETTINGS.route) {
                            AppSettingsPage(navController, Modifier.padding(innerPadding))
                        }
                        composable(Routes.MORE_MAIN_SETTINGS_APPEARANCE.route) {
                            AppearanceSettingsPage(
                                navController = navController,
                                Modifier.padding(innerPadding)
                            )
                        }
                        composable(Routes.MORE_MAIN_SETTINGS_APPLICATION.route) {
                            ApplicationPreferencesPage(
                                navController = navController,
                                Modifier.padding(innerPadding)
                            )
                        }
                    }
                    composable(
                        Routes.APP_UPDATE.route + "/{updateUrl}/{latestAppVersion}/{releaseUrl}"
                    ) { backStackEntry ->
                        AppUpdatePage(
                            navController,
                            updateUrl = backStackEntry.arguments?.getString("updateUrl") ?: "",
                            latestAppVersion = backStackEntry.arguments?.getString("latestAppVersion") ?: "",
                            releaseUrl = backStackEntry.arguments?.getString("releaseUrl") ?: "",
                            modifier = Modifier.padding(innerPadding)
                        )
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