package com.honorida.ui.components.navigation

import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import com.honorida.domain.extensions.replaceValues

enum class Routes(
    val route: String,
    val showNavBar: Boolean = true,
    val subRouteOf: Routes? = null
) {
    LIBRARY("library"),
    HISTORY("history"),
    MORE("more"),
    MORE_MAIN("${MORE}/main", subRouteOf = MORE),
    MORE_MAIN_ABOUT("${MORE}/about", subRouteOf = MORE, showNavBar = false),
    MORE_MAIN_SETTINGS("${MORE_MAIN}/settings", subRouteOf = MORE, showNavBar = false),
    MORE_MAIN_SETTINGS_APPEARANCE(
        "${MORE_MAIN_SETTINGS}/appearanceSettings",
        subRouteOf = MORE_MAIN_SETTINGS,
        showNavBar = false
    ),
    MORE_MAIN_SETTINGS_APPLICATION(
        "${MORE_MAIN_SETTINGS}/application",
        subRouteOf = MORE_MAIN_SETTINGS,
        showNavBar = false
    ),
    APP_UPDATE(
        "appUpdate?" +
                "${Extras.ReleaseId.key}={${Extras.ReleaseId.key}}",
        showNavBar = false
    )
}

fun Routes.withArgs(vararg args: String) : String {
    return buildString {
        append(route)
        args.forEach {
            append("/$it")
        }
    }
}

fun getAppUpdateUri(updateInfo: CheckUpdateResponse) : String {
    return Routes.APP_UPDATE.route.replaceValues(mapOf(
        Extras.ReleaseId.key to updateInfo.releaseId.toString(),
    ))
}