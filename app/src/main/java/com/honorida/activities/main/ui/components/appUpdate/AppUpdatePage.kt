package com.honorida.activities.main.ui.components.appUpdate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.honorida.HonoridaApp
import com.honorida.R
import com.honorida.activities.main.ui.viewModels.AppUpdateViewModel
import com.honorida.activities.main.ui.viewModels.helpers.viewModelFactory

@Composable
fun AppUpdatePage(
    updateUrl: String,
    latestAppVersion: String,
    releaseUrl: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AppUpdateViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = viewModelFactory {
            AppUpdateViewModel(
                HonoridaApp.appModule.downloader
            )
        }
    )
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Icon(
                imageVector = Icons.Filled.NotificationsActive,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )
            Text(
                text = stringResource(R.string.new_version_available),
                fontSize = 30.sp
            )
            Text(
                text = latestAppVersion,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Column {
            OutlinedButton(
                onClick = {
                    uriHandler.openUri(releaseUrl)
                },
                modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.check_on_github)
                )
            }
            Divider(Modifier.padding(vertical = 10.dp))
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                onClick = {
                    viewModel.downloadUpdate(context, updateUrl)
                }
            ) {
                Text(
                    text = stringResource(R.string.download)
                )
            }
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Text(
                    text = stringResource(R.string.not_now)
                )
            }
        }
    }
}