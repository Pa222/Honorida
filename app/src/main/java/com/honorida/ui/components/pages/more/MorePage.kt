package com.honorida.ui.components.pages.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.R
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.shared.ApplicationLogo
import com.honorida.ui.components.shared.SettingsListColumn

@Composable
fun MorePage(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val items = remember {
        listOf(
            NavigationListItemModel(
                icon = Icons.Outlined.Settings,
                text = context.getString(R.string.settings),
                onClick = {
                    navController.navigate(Routes.MORE_MAIN_SETTINGS.route)
                }
            ),
            NavigationListItemModel(
                Icons.Outlined.Info,
                context.getString(R.string.about),
                onClick = {
                    navController.navigate(Routes.MORE_MAIN_ABOUT.route)
                }
            )
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ApplicationLogo(modifier = Modifier.padding(vertical = 30.dp))
        HorizontalDivider()
        SettingsListColumn(
            modifier = Modifier
                .padding(vertical = 20.dp),
        ) {
            items.forEach {
                NavigationListItem(item = it)
            }
        }
    }
}

@Preview
@Composable
private fun MorePagePreview() {
    val navController = rememberNavController()
    MorePage(navController)
}