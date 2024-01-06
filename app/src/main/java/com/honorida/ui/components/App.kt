package com.honorida.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.honorida.R
import com.honorida.ui.components.navigation.NavBar
import com.honorida.ui.components.navigation.NavTab
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.pages.history.HistoryPage
import com.honorida.ui.components.pages.library.LibraryPage
import com.honorida.ui.components.pages.more.MorePage
import com.honorida.ui.theme.HonoridaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
    val navController = rememberNavController()

    val navBarItems = listOf(
        NavTab(stringResource(R.string.library), Routes.Library.route, Icons.Filled.Home, Icons.Filled.Home),
        NavTab(stringResource(R.string.history), Routes.History.route, Icons.Filled.History, Icons.Filled.History),
        NavTab(stringResource(R.string.more), Routes.More.route, Icons.Filled.MoreHoriz, Icons.Filled.MoreHoriz)
    )

    HonoridaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Scaffold(
                bottomBar = {
                    NavBar(navBarItems, navController)
                }
            ) { innerPadding ->
                NavHost(navController = navController, startDestination = Routes.Library.route) {
                    composable(Routes.Library.route) {
                        LibraryPage(Modifier.padding(innerPadding))
                    }
                    composable(Routes.History.route) {
                        HistoryPage(Modifier.padding(innerPadding))
                    }
                    composable(Routes.More.route) {
                        MorePage(Modifier.padding(innerPadding))
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