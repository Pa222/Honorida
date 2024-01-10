package com.honorida.activities.main.ui.components.navigation

import com.honorida.data.external.models.CheckUpdateResponse
import com.honorida.domain.constants.Extras
import java.net.URLEncoder

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
        "appUpdate",
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

fun Routes.toAppUpdate(info: CheckUpdateResponse) : String {
    return buildString {
        append(route)
        append('/')
        append(URLEncoder.encode(info.updateUrl, "utf-8"))
        append('/')
        append(info.latestAppVersion)
        append('/')
        append(URLEncoder.encode(info.releaseUrl, "utf-8"))
    }
}