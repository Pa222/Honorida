package com.honorida.ui.components.pages.more.subPages.appSettings.subPages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.data.local.enums.toSelectControlValue
import com.honorida.representation.viewModels.AppearanceSettingsViewModel
import com.honorida.ui.components.pages.more.subPages.appSettings.components.SettingsCategory
import com.honorida.ui.components.shared.SettingsListColumn
import com.honorida.ui.components.shared.controls.selectControl.SelectControl
import com.honorida.ui.components.topbar.TopBar

@Composable
fun AppearanceSettingsPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AppearanceSettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    val darkThemePreference = uiState.darkThemePreference
    val darkThemePreferenceSelectOptionList = remember {
        DarkThemePreference.getSelectControlOptions(context)
    }

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
            ) {
                SelectControl(
                    text = stringResource(R.string.dark_theme),
                    value = darkThemePreference.toSelectControlValue(context),
                    options = darkThemePreferenceSelectOptionList,
                    onChange = {
                        viewModel.updateDarkThemeSetting(it)
                    }
                )
            }
        }
    }
}
