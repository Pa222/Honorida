package com.honorida.ui.components.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.honorida.R

@Composable
fun NavBar(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val tabBarItems = remember {
        listOf(
            NavTab(
                context.getString(R.string.library), Routes.LIBRARY.route, Icons.Filled.Home,
                Icons.Outlined.Home),
            NavTab(
                context.getString(R.string.history), Routes.HISTORY.route, Icons.Filled.History,
                Icons.Outlined.History),
            NavTab(
                context.getString(R.string.more), Routes.MORE_MAIN.route, Icons.Filled.MoreHoriz,
                Icons.Outlined.MoreHoriz)
        )
    }

    var showNavBar = false

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != null) {
        val route = Routes.entries.find { it.route == currentRoute }
        if (route != null) {
            showNavBar = route.showNavBar
            val selectedTab = tabBarItems.find { it.route == route.route }
            selectedTabIndex = tabBarItems.indexOf(selectedTab)
        }
    }

    val activity = (LocalContext.current as? Activity)

    if (showNavBar){
        BackHandler(enabled = true, onBack = {
            val route = Routes.entries.find { it.route == currentRoute }
            if (route != null) {
                if (route.subRouteOf != null) {
                    navController.popBackStack()
                }
                else {
                    if (selectedTabIndex != 0) {
                        selectedTabIndex = 0
                        navController.navigate(Routes.LIBRARY.route)
                    } else {
                        activity?.finish()
                    }
                }
            }
        })
        NavigationBar(modifier = modifier) {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        if (selectedTabIndex != index) {
                            selectedTabIndex = index
                            navController.navigate(tabBarItem.route)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selectedTabIndex == index) {tabBarItem.selectedIcon}
                            else {tabBarItem.unselectedIcon},
                            contentDescription = tabBarItem.title
                        )
                    },
                    label = {
                        Text(tabBarItem.title)
                    },
                )
            }
        }
    }
}