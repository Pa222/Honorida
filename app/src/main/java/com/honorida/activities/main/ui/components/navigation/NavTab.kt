package com.honorida.activities.main.ui.components.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavTab(val title: String,
                    val route: String,
                    val selectedIcon: ImageVector,
                    val unselectedIcon: ImageVector)