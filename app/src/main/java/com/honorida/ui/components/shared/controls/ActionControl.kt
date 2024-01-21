package com.honorida.ui.components.shared.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.honorida.domain.constants.Permissions
import com.honorida.ui.components.shared.PermissionsDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ActionControl(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    description: String? = null,
    requiredPermissions: List<Permissions> = emptyList(),
    onClick: () -> Unit
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

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = text,
            )
        }
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Text(
                text = text,
            )
            if (description != null) {
                Text(
                    text = description,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}