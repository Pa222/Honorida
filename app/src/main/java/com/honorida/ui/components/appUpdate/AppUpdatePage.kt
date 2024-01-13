package com.honorida.ui.components.appUpdate

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.domain.constants.LoadingState
import com.honorida.representation.viewModels.AppUpdateViewModel

@Composable
fun AppUpdatePage(
    releaseId: Int,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AppUpdateViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val uiState = viewModel.uiState.collectAsState().value
    val releaseInfo = uiState.releaseInfo

    if (uiState.loadingState == LoadingState.Idle) {
        viewModel.loadReleaseInfo(releaseId)
    }
    else if (uiState.loadingState == LoadingState.Failed) {
        navController.navigateUp()
        Toast.makeText(
            context,
            stringResource(R.string.failed_to_retrieve_release_info),
            Toast.LENGTH_LONG
        ).show()
    }
    else if (uiState.loadingState == LoadingState.Loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
            )
        }
    }
    else if (uiState.loadingState == LoadingState.Loaded && releaseInfo != null) {
        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .heightIn(max = (screenHeight * 0.3).dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Icon(
                    imageVector = Icons.Filled.NotificationsActive,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
                Text(
                    text = stringResource(R.string.new_version_available),
                    fontSize = 30.sp,
                    lineHeight = 40.sp
                )
                Text(
                    text = uiState.releaseInfo.versionName,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            Column(
                modifier = Modifier
                    .heightIn(max = (screenHeight * 0.4).dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = uiState.releaseInfo.releaseNotes,
                )
            }
            Column(
                modifier = Modifier
                    .heightIn(max = (screenHeight * 0.3).dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedButton(
                    onClick = {
                        try {
                            uriHandler.openUri(uiState.releaseInfo.gitHubUrl)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(
                                context,
                                context.getString(R.string.failed_to_open_link),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.check_on_github)
                    )
                }
                HorizontalDivider(Modifier.padding(vertical = 10.dp))
                FilledTonalButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    onClick = {
                        viewModel.startUpdate(uiState.releaseInfo)
                        navController.navigateUp()
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
    else {
        navController.navigateUp()
        Toast.makeText(
            context,
            stringResource(R.string.failed_to_retrieve_release_info),
            Toast.LENGTH_LONG
        ).show()
    }
}