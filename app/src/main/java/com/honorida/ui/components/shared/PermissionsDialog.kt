package com.honorida.ui.components.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness1
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.honorida.R
import com.honorida.domain.constants.Permissions
import com.honorida.ui.components.shared.viewModels.PermissionsDialogViewModel

@Composable
fun PermissionsDialog(
    permissions: List<Permissions>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PermissionsDialogViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = modifier
                .width((width * 0.7).dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = stringResource(
                        R.string.this_feature_requires_following_permissions
                    )
                )
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 20.dp)
                ) {
                    permissions.forEach {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Brightness1,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(10.dp)
                            )
                            Text(text = it.name)
                        }
                    }
                }
                Text(
                    text = stringResource(
                        R.string.please_enable_them_in_settings_and_return_to_the_app
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                TextButton(
                    onClick = {
                        viewModel.openAppSettings(context)
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.go_to_settings),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
