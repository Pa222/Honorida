package com.honorida.ui.components.navigation

enum class Routes(
    val route: String,
    val showNavBar: Boolean = true
) {
    LIBRARY("library"),
    HISTORY("history"),
    MORE("more"),
    APP_UPDATE("appUpdate", showNavBar = false)
}