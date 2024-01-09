package com.honorida.activities.main.ui.components.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.honorida.activities.main.ui.components.navigation.Routes
import com.honorida.activities.main.ui.components.pages.history.HistoryPage
import com.honorida.activities.main.ui.components.pages.library.LibraryPage
import com.honorida.activities.main.ui.components.pages.more.MorePage
import com.honorida.activities.main.ui.components.pages.more.subPages.about.AboutPage
import com.honorida.activities.main.ui.components.pages.more.subPages.appSettings.AppSettingsPage
import com.honorida.activities.main.ui.components.pages.more.subPages.appSettings.subPages.AppearanceSettingsPage
import com.honorida.activities.main.ui.components.pages.more.subPages.appSettings.subPages.ApplicationPreferencesPage

fun NavGraphBuilder.buildMorePageNavGraph (
    navController: NavController
) {
    this.navigation(
        route = Routes.MORE.route,
        startDestination = Routes.MORE_MAIN.route,
    ) {
        composable(Routes.MORE_MAIN.route) {
            MorePage(navController = navController)
        }
        composable(Routes.MORE_MAIN_ABOUT.route) {
            AboutPage(navController = navController)
        }
        composable(Routes.MORE_MAIN_SETTINGS.route) {
            AppSettingsPage(navController)
        }
        composable(Routes.MORE_MAIN_SETTINGS_APPEARANCE.route) {
            AppearanceSettingsPage(
                navController = navController
            )
        }
        composable(Routes.MORE_MAIN_SETTINGS_APPLICATION.route) {
            ApplicationPreferencesPage(
                navController = navController
            )
        }
    }
}

fun NavGraphBuilder.buildHistoryPageNavGraph () {
    this.composable(Routes.HISTORY.route) {
        HistoryPage()
    }
}

fun NavGraphBuilder.buildLibraryPageNavGraph () {
    this.composable(Routes.LIBRARY.route) {
        LibraryPage()
    }
}