package com.honorida.ui.components.storageSetUp

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.domain.activityResultContracts.PermissibleOpenDocumentTreeContract
import com.honorida.domain.helpers.checkUriPersisted
import com.honorida.domain.helpers.getDisplayPath
import com.honorida.representation.viewModels.StorageSetUpViewModel
import com.honorida.ui.components.navigation.Routes
import java.util.Objects


@Composable
fun StorageSetUpPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: StorageSetUpViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var selectedUri: Uri? by remember {
        mutableStateOf(null)
    }
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
                selectedUri = uri
            }
        }
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Icon(
            imageVector = Icons.Filled.Storage,
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.storage_setup),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.8).dp)
        )
        Spacer(modifier = Modifier.height(80.dp))
        TextButton(
            onClick = { dirPickerLauncher.launch(Uri.EMPTY) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.open_directory_picker))
        }
        if (selectedUri != null) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(R.string.selected_storage))
            Text(text = getDisplayPath(context, selectedUri!!))
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = {
                    viewModel.saveStorageConfigured(selectedUri!!)
                    navController.popBackStack()
                    navController.navigate(Routes.LIBRARY.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.finish))
            }
        }
    }
}
