package com.honorida.ui.components.pages.more.subPages.appSettings.subPages

import android.os.Build
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
import com.honorida.data.models.protoStore.UpdatesPreferences
import com.honorida.domain.constants.Permissions
import com.honorida.representation.viewModels.ApplicationPreferencesViewModel
import com.honorida.representation.viewModels.helpers.viewModelFactory
import com.honorida.ui.components.pages.more.subPages.appSettings.components.SettingsCategory
import com.honorida.ui.components.shared.SettingsListColumn
import com.honorida.ui.components.shared.controls.CheckBoxControl
import com.honorida.ui.components.topbar.TopBar

@Composable
fun ApplicationPreferencesPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ApplicationPreferencesViewModel = viewModel(
        factory = viewModelFactory {
            ApplicationPreferencesViewModel(
                HonoridaApp.appModule.protoDataStore
            )
        }
    )
) {
    val uiState = viewModel.uiState.collectAsState().value
    val updatesPreferences = uiState.updatesPreferences
        .collectAsState(initial = UpdatesPreferences()).value

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
                val requiredPermissions = mutableListOf<Permissions>()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requiredPermissions.add(Permissions.PostNotifications)
                }
                CheckBoxControl(
                    text = stringResource(R.string.receive_app_updates),
                    checked = updatesPreferences.receiveAppUpdates,
                    onChange = {
                        viewModel.updateReceiveAppUpdatesPreference(it)
                    },
                    displayChildren = updatesPreferences.receiveAppUpdates,
                    requiredPermissions = requiredPermissions
                ) {
                    CheckBoxControl(
                        text = stringResource(R.string.check_application_updates_on_startup),
                        checked = updatesPreferences.checkUpdatesOnStartUp,
                        onChange = {
                            viewModel.updateCheckUpdatesOnStartUpPreference(it)
                        }
                    )
                    CheckBoxControl(
                        text = stringResource(R.string.receive_pre_release_versions),
                        checked = updatesPreferences.receivePreReleaseVersions,
                        onChange = {
                            //
                        },
                        enabled = false
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApplicationPreferencesPagePreview() {
    ApplicationPreferencesPage(navController = rememberNavController())
}