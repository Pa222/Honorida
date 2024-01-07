package com.honorida.ui.components.pages.more.subPages.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.BuildConfig
import com.honorida.HonoridaApp
import com.honorida.R
import com.honorida.ui.components.pages.more.NavigationListItem
import com.honorida.ui.components.pages.more.NavigationListItemModel
import com.honorida.ui.components.shared.ApplicationLogo
import com.honorida.ui.components.topbar.TopBar
import com.honorida.ui.viewModels.AboutPageViewModel
import com.honorida.ui.viewModels.helpers.viewModelFactory

@Composable
fun AboutPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AboutPageViewModel = viewModel(
        factory = viewModelFactory {
            AboutPageViewModel(
                HonoridaApp.appModule.appUpdater
            )
        }
    )
) {
    val context = LocalContext.current
    val appVersion = rememberSaveable {
        BuildConfig.VERSION_NAME
    }
    val versionDescription = rememberSaveable {
        val stringFormat = if (appVersion.contains(context.getText(R.string.alpha))) {
            context.getString(R.string.preRelease_app_version_format).toString()
        } else {
            context.getString(R.string.stable_app_version_format).toString()
        }
        String.format(stringFormat, appVersion)
    }
    val items = remember {
        listOf(
            NavigationListItemModel(
                text = context.getString(R.string.app_version),
                description = versionDescription
            ),
            NavigationListItemModel(
                text = context.getString(R.string.check_for_updates),
                onClick = {
                    viewModel.checkForUpdates(
                        context,
                        appVersion,
                        navController
                    )
                }
            )
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.about),
                navigateBackAction = {
                    navController.navigateUp()
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ApplicationLogo(modifier = Modifier.padding(vertical = 30.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                items.forEach {
                    NavigationListItem(item = it)
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutPagePreview() {
    AboutPage(navController = rememberNavController())
}