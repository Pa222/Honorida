package com.honorida.ui.components.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.honorida.domain.constants.Extras
import com.honorida.ui.components.appUpdate.AppUpdatePage
import com.honorida.ui.components.navigation.DeepLinks
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.pages.bookPreview.BookPreviewPage
import com.honorida.ui.components.pages.bookReader.BookReader
import com.honorida.ui.components.pages.history.HistoryPage
import com.honorida.ui.components.pages.library.LibraryPage
import com.honorida.ui.components.pages.more.MorePage
import com.honorida.ui.components.pages.more.subPages.about.AboutPage
import com.honorida.ui.components.pages.more.subPages.appSettings.AppSettingsPage
import com.honorida.ui.components.pages.more.subPages.appSettings.subPages.AppearanceSettingsPage
import com.honorida.ui.components.pages.more.subPages.appSettings.subPages.ApplicationPreferencesPage
import com.honorida.ui.components.pages.more.subPages.appSettings.subPages.StorageSettingsPage
import com.honorida.ui.components.storageSetUp.StorageSetUpPage

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
        composable(Routes.MORE_MAIN_SETTINGS_STORAGE.route) {
            StorageSettingsPage(
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

fun NavGraphBuilder.buildBooksNavGraph(navController: NavController) {
    this.composable(
        Routes.LIBRARY_BOOK_PREVIEW.route,
        arguments = listOf(
            navArgument(Extras.BookId.key) {
                type = NavType.IntType
            }
        )
    ) {
        BookPreviewPage(
            navController,
        )
    }
    this.composable(
        Routes.LIBRARY_BOOK_READER.route,
        arguments = listOf(
            navArgument(Extras.BookId.key) {
                type = NavType.IntType
            },
            navArgument(Extras.BookResourceId.key) {
                type = NavType.StringType
            }
        )
    ) {
        BookReader(
            navController = navController
        )
    }
}

fun NavGraphBuilder.buildLibraryPageNavGraph (
    navController: NavController
) {
    this.composable(
        Routes.LIBRARY.route,
        deepLinks = listOf(DeepLinks.Library.link)
    ) {
        LibraryPage(navController)
    }
}

fun NavGraphBuilder.buildAppUpdateNavGraph (
    navController: NavController
) {
    this.composable(
        route = Routes.APP_UPDATE.route,
        deepLinks = listOf(DeepLinks.AppUpdate.link),
        arguments = listOf(
            navArgument(Extras.ReleaseId.key) {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        AppUpdatePage(
            releaseId = backStackEntry.arguments?.getInt(Extras.ReleaseId.key) ?: 0,
            navController = navController,
        )
    }
}

fun NavGraphBuilder.buildStorageSetAppNavGraph (
    navController: NavController
) {
    this.composable(
        route = Routes.STORAGE_SETUP.route,
    ) {
        StorageSetUpPage(navController)
    }
}