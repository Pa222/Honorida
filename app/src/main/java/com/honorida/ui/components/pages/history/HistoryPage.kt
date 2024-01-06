package com.honorida.ui.components.pages.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.honorida.R
import com.honorida.ui.components.topbar.TopBar
import com.honorida.ui.theme.HonoridaTheme

@Composable
fun HistoryPage(modifier: Modifier = Modifier) {
    HonoridaTheme {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.history)
                )
            },
            modifier = modifier
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {

            }
        }
    }
}

@Preview
@Composable
fun HistoryPagePreview() {
    HistoryPage()
}