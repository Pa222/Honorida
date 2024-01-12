package com.honorida.activities.main.ui.components.shared.controls

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.honorida.R
import com.honorida.activities.main.ui.components.shared.PermissionsDialog
import com.honorida.domain.constants.Permissions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckBoxControl(
    text: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    displayChildren: Boolean = false,
    requiredPermissions: List<Permissions> = emptyList(),
    enabled: Boolean = true,
    children: (@Composable () -> Unit)? = null,
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
                    onChange(!checked)
                }
            }
        )
        if (!permissionsState.allPermissionsGranted && checked) {
            onChange(false)
        }
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(
                    text = text,
                )
                if (description != null) {
                    Text(
                        text = description,
                        fontSize = 10.sp,
                    )
                }
            }
            Switch(
                checked = checked,
                enabled = enabled,
                onCheckedChange = {
                    if (permissionsState != null && !permissionsState.allPermissionsGranted) {
                        if (permissionsState.shouldShowRationale) {
                            permissionsState.launchMultiplePermissionRequest()
                        } else {
                            showPermissionsDialog = true
                        }
                    } else {
                        onChange(it)
                    }
                }
            )
        }
        if (displayChildren && children != null) {
            Row(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                children()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckBoxControlPreview() {
    CheckBoxControl(
        text = stringResource(R.string.check_application_updates_on_startup),
        description = "Example description",
        checked = false,
        onChange = {},
        displayChildren = true,
    ) {
        CheckBoxControl(
            text = "Test children setting",
            checked = false,
            onChange = {  }
        )
    }
}