package com.honorida.ui.components.pages.more.subPages.appSettings.subPages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.HonoridaApp
import com.honorida.R
import com.honorida.data.models.protoStore.ApplicationPreferences
import com.honorida.ui.components.pages.more.subPages.appSettings.components.SettingsCategory
import com.honorida.ui.components.shared.SettingsListColumn
import com.honorida.ui.components.shared.controls.CheckBoxControl
import com.honorida.ui.components.topbar.TopBar
import com.honorida.ui.viewModels.ApplicationPreferencesViewModel
import com.honorida.ui.viewModels.helpers.viewModelFactory

@Composable
fun ApplicationPreferencesPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ApplicationPreferencesViewModel = viewModel(
        factory = viewModelFactory {
            ApplicationPreferencesViewModel(
                HonoridaApp.appModule.protoDataStore.applicationPreferences
            )
        }
    )
) {
    val uiState = viewModel.uiState.collectAsState().value
    val preferences = uiState.preferences.collectAsState(initial = ApplicationPreferences()).value

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = stringResource(R.string.application),
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
                title = stringResource(R.string.application_updates),
            ) {
                CheckBoxControl(
                    text = stringResource(R.string.check_application_updates_on_startup),
                    checked = preferences.checkUpdatesOnStartUp,
                    onChange = {
                        viewModel.updateCheckForUpdatesOnStartUp(it)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun ApplicationPreferencesPagePreview() {
    ApplicationPreferencesPage(navController = rememberNavController())
}