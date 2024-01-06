package com.honorida.ui.components.pages.appUpdate

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.R
import com.honorida.ui.components.navigation.Routes

@Composable
fun AppUpdatePage(navController: NavController, modifier: Modifier = Modifier) {
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
                text = "v1.0.0-alpha.2",
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.open_on_github)
                )
            }
        }
        Column {
            Divider(Modifier.padding(vertical = 10.dp))
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = stringResource(R.string.download)
                )
            }
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(
                        Routes.LIBRARY.route,
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.not_now)
                )
            }
        }
    }
}

@Preview
@Composable
fun AppUpdatePreview() {
    val navController = rememberNavController()
    AppUpdatePage(navController)
}