package com.honorida.ui.components.pages.more.subPages.appSettings.subPages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.R
import com.honorida.ui.components.pages.more.subPages.appSettings.components.SettingsCategory
import com.honorida.ui.components.shared.SettingsListColumn
import com.honorida.ui.components.topbar.TopBar

@Composable
fun AppearanceSettingsPage(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = stringResource(R.string.appearance),
                navigateBackAction = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        SettingsListColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            SettingsCategory(
                title = stringResource(R.string.theme),
            )
        }
    }
}

@Preview
@Composable
private fun AppearanceSettingsPagePreview() {
    val navController = rememberNavController()
    AppearanceSettingsPage(navController = navController)
}