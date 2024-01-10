package com.honorida.activities.main.ui.components.navigation.graphs

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.honorida.activities.main.ui.components.appUpdate.AppUpdatePage
import com.honorida.activities.main.ui.components.navigation.DeepLinks
import com.honorida.activities.main.ui.components.navigation.Routes
import com.honorida.activities.main.ui.components.pages.history.HistoryPage
import com.honorida.activities.main.ui.components.pages.library.LibraryPage
import com.honorida.activities.main.ui.components.pages.more.MorePage
import com.honorida.activities.main.ui.components.pages.more.subPages.about.AboutPage
import com.honorida.activities.main.ui.components.pages.more.subPages.appSettings.AppSettingsPage
import com.honorida.activities.main.ui.components.pages.more.subPages.appSettings.subPages.AppearanceSettingsPage
import com.honorida.activities.main.ui.components.pages.more.subPages.appSettings.subPages.ApplicationPreferencesPage
import com.honorida.domain.constants.Extras

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
    this.composable(
        Routes.HISTORY.route,
        deepLinks = listOf(DeepLinks.History.link)
    ) {
        HistoryPage()
    }
}

fun NavGraphBuilder.buildLibraryPageNavGraph () {
    this.composable(
        Routes.LIBRARY.route,
        deepLinks = listOf(DeepLinks.Library.link)
    ) {
        LibraryPage()
    }
}

fun NavGraphBuilder.buildAppUpdateNavGraph (
    navController: NavController
) {
    this.composable(
        route = Routes.APP_UPDATE.route,
        deepLinks = listOf(DeepLinks.AppUpdate.link),
    ) { backStackEntry ->
        AppUpdatePage(
            updateUrl = backStackEntry.arguments?.getString(Extras.UpdateUrl.key) ?: "",
            latestAppVersion = backStackEntry.arguments?.getString(Extras.LatestAppVersion.key) ?: "",
            releaseUrl = backStackEntry.arguments?.getString(Extras.ReleaseUrl.key) ?: "",
            navController = navController,
        )
    }
}