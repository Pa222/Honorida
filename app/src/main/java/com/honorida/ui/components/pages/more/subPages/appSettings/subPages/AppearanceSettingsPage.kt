package com.honorida.ui.components.pages.more.subPages.appSettings.subPages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.R
import com.honorida.ui.components.pages.more.subPages.appSettings.components.SettingsCategory
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
        ) {
            SettingsCategory(
                title = stringResource(R.string.theme),
            )
        }
    }
}

@Preview
@Composable
fun AppearanceSettingsPagePreview() {
    val navController = rememberNavController()
    AppearanceSettingsPage(navController = navController)
}