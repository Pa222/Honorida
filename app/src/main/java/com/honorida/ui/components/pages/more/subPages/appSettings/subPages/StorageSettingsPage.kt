package com.honorida.ui.components.pages.more.subPages.appSettings.subPages

import android.content.Intent
import android.net.Uri
import android.os.Build
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
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.Permissions
import com.honorida.domain.helpers.getDisplayPath
import com.honorida.domain.helpers.isUriPersisted
import com.honorida.domain.services.foreground.StorageTransferForegroundService
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
                if (uiState.selectedStorage == uri.toString()) {
                    return@let
                }
                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                if (context.contentResolver.isUriPersisted(uri)) {
                    context.contentResolver.releasePersistableUriPermission(uri, flags)
                }
                context.contentResolver.takePersistableUriPermission(uri, flags)
                Intent(context, StorageTransferForegroundService::class.java)
                    .apply {
                        putExtra(Extras.SourceStorageUri.key, uiState.selectedStorage)
                        putExtra(Extras.TargetStorageUri.key, uri.toString())
                    }
                    .also {
                        context.startService(it)
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
            val requiredPermissions = mutableListOf<Permissions>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requiredPermissions.add(Permissions.PostNotifications)
            }
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
                requiredPermissions = requiredPermissions,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}
