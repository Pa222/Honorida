package com.honorida.ui.components.shared.buttons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.honorida.domain.constants.Permissions
import com.honorida.ui.components.shared.PermissionsDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FloatingActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    requiredPermissions: List<Permissions> = emptyList(),
) {
    var permissionsState: MultiplePermissionsState? = null
    var showPermissionsDialog by remember {
        mutableStateOf(false)
    }
    if (requiredPermissions.isNotEmpty()) {
        permissionsState = rememberMultiplePermissionsState(
            permissions = requiredPermissions.map { it.permission },
            onPermissionsResult = {
                val isAllGranted = !it.containsValue(false)
                if (isAllGranted) {
                    onClick()
                }
            }
        )
    }

    if (showPermissionsDialog && permissionsState != null) {
        PermissionsDialog(
            permissions = permissionsState.permissions.filter {
                it.status != PermissionStatus.Granted
            }.map {
                Permissions.getByString(it.permission)!!
            },
            onDismissRequest = { showPermissionsDialog = false }
        )
    }

    FilledTonalIconButton(
        modifier = modifier,
        onClick = {
            if (permissionsState != null && !permissionsState.allPermissionsGranted) {
                if (permissionsState.shouldShowRationale) {
                    permissionsState.launchMultiplePermissionRequest()
                }
                else {
                    showPermissionsDialog = true
                }
            }
            else {
                onClick()
            }
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        )
    }
}