package com.honorida.ui.components.pages.more.subPages.appSettings.subPages

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.domain.activityResultContracts.PermissibleOpenDocumentTreeContract
import com.honorida.domain.helpers.checkUriPersisted
import com.honorida.domain.helpers.getDisplayPath
import com.honorida.representation.viewModels.StorageSettingsViewModel
import com.honorida.ui.components.shared.SettingsListColumn
import com.honorida.ui.components.shared.controls.ActionControl
import com.honorida.ui.components.topbar.TopBar

@Composable
fun StorageSettingsPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: StorageSettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    val dirPickerLauncher = rememberLauncherForActivityResult(
        contract = PermissibleOpenDocumentTreeContract(true),
        onResult = { maybeUri ->
            maybeUri?.let { uri ->
                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                if (checkUriPersisted(context.contentResolver, uri)) {
                    context.contentResolver.releasePersistableUriPermission(uri, flags)
                }
                context.contentResolver.takePersistableUriPermission(uri, flags)
                if (checkUriPersisted(context.contentResolver, uiState.selectedStorage.toUri())) {
                    context.contentResolver.releasePersistableUriPermission(
                        uiState.selectedStorage.toUri(),
                        flags
                    )
                }
                viewModel.updateSelectedStorage(uri)
            }
        }
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = stringResource(R.string.data_storage),
                navigateBackAction = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        SettingsListColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 0.dp)
        ) {
            ActionControl(
                text = stringResource(R.string.storage_location),
                description = getDisplayPath(context, uiState.selectedStorage.toUri()),
                onClick = {
                    dirPickerLauncher.launch(
                        if (uiState.selectedStorage != "") {
                            uiState.selectedStorage.toUri()
                        }
                        else {
                            Uri.EMPTY
                        }
                    )
                },
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}
