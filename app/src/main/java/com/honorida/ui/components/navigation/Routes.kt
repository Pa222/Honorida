package com.honorida.ui.components.navigation

enum class Routes(
    val route: String,
    val showNavBar: Boolean = true
) {
    Library("library"),
    History("history"),
    More("more"),
    AppUpdate("appUpdate", showNavBar = false)
}