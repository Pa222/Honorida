package com.honorida.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.honorida.ui.theme.HonoridaTheme

@Composable
fun NavBar(tabBarItems: List<NavTab>, navController: NavController, modifier: Modifier = Modifier) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    HonoridaTheme {
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
                    label = {Text(tabBarItem.title)},
                )
            }
        }
    }
}