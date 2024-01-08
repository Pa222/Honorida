package com.honorida.ui.components.pages.more.subPages.appSettings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.R
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.pages.more.NavigationListItem
import com.honorida.ui.components.pages.more.NavigationListItemModel
import com.honorida.ui.components.shared.SettingsListColumn
import com.honorida.ui.components.topbar.TopBar

@Composable
fun AppSettingsPage(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val items = remember {
        listOf(
            NavigationListItemModel(
                Icons.Outlined.ColorLens,
                context.getString(R.string.appearance),
                context.getString(R.string.appearance_setting_description),
                onClick = {
                    navController.navigate(Routes.MORE_MAIN_SETTINGS_APPEARANCE.route)
                }
            ),
            NavigationListItemModel(
                Icons.Outlined.SettingsApplications,
                text = context.getString(R.string.application),
                description = context.getString(R.string.application_settings_description),
                onClick = {
                    navController.navigate(Routes.MORE_MAIN_SETTINGS_APPLICATION.route)
                }
            )
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = stringResource(R.string.settings),
                navigateBackAction = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        SettingsListColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items.forEach {
                NavigationListItem(item = it)
            }
        }
    }

}

@Preview
@Composable
private fun AppSettingsPreview() {
    val navController = rememberNavController()
    AppSettingsPage(navController)
}