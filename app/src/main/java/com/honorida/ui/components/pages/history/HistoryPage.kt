package com.honorida.ui.components.pages.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R
import com.honorida.ui.components.topbar.TopBar

@Composable
fun HistoryPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.history)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Build,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(60.dp)
            )
            Text(
                text = stringResource(R.string.under_development),
                fontSize = 24.sp
            )
        }
    }
}

@Preview
@Composable
private fun HistoryPagePreview() {
    HistoryPage()
}