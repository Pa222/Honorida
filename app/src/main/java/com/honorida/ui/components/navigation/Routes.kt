package com.honorida.ui.components.navigation

sealed class Routes(val route: String) {
    data object Library: Routes("library")
    data object History: Routes("history")
    data object More: Routes("more")
}