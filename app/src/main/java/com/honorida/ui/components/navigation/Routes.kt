package com.honorida.ui.components.navigation

import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import com.honorida.domain.helpers.replaceValues
import com.honorida.representation.uiStates.BookInfo

enum class Routes(
    val route: String,
    val showNavBar: Boolean = true,
    val subRouteOf: Routes? = null
) {
    LIBRARY("library"),
    LIBRARY_BOOK_PREVIEW(
        "${LIBRARY.route}/bookPreview?${Extras.BookId.key}={${Extras.BookId.key}}",
        showNavBar = false
    ),
    LIBRARY_BOOK_READER(
        route = "${LIBRARY.route}/bookReader?" +
                "${Extras.BookId.key}={${Extras.BookId.key}}&" +
                "${Extras.BookResourceId.key}={${Extras.BookResourceId.key}}",
        showNavBar = false
    ),
    HISTORY("history"),
    MORE("more"),
    MORE_MAIN("${MORE.route}/main", subRouteOf = MORE),
    MORE_MAIN_ABOUT("${MORE.route}/about", subRouteOf = MORE, showNavBar = false),
    MORE_MAIN_SETTINGS("${MORE_MAIN.route}/settings", subRouteOf = MORE, showNavBar = false),
    MORE_MAIN_SETTINGS_APPEARANCE(
        "${MORE_MAIN_SETTINGS.route}/appearanceSettings",
        subRouteOf = MORE_MAIN_SETTINGS,
        showNavBar = false
    ),
    MORE_MAIN_SETTINGS_APPLICATION(
        "${MORE_MAIN_SETTINGS.route}/application",
        subRouteOf = MORE_MAIN_SETTINGS,
        showNavBar = false
    ),
    MORE_MAIN_SETTINGS_STORAGE(
        "${MORE_MAIN_SETTINGS.route}/storage",
        subRouteOf = MORE_MAIN_SETTINGS,
        showNavBar = false
    ),
    APP_UPDATE(
        "appUpdate?" +
                "${Extras.ReleaseId.key}={${Extras.ReleaseId.key}}",
        showNavBar = false
    ),
    STORAGE_SETUP(
        "storageSetup",
        showNavBar = false
    )
}

fun getAppUpdateUri(updateInfo: CheckUpdateResponse) : String {
    return Routes.APP_UPDATE.route.replaceValues(mapOf(
        Extras.ReleaseId.key to updateInfo.releaseId.toString(),
    ))
}

fun getBookPreviewUri(bookId: Int): String {
    return Routes.LIBRARY_BOOK_PREVIEW.route.replaceValues(mapOf(
        Extras.BookId.key to bookId.toString()
    ))
}

fun getBookReaderUri(bookId: Int, resourceId: String): String {
    return Routes.LIBRARY_BOOK_READER.route.replaceValues(mapOf(
        Extras.BookId.key to bookId.toString(),
        Extras.BookResourceId.key to resourceId
    ))
}