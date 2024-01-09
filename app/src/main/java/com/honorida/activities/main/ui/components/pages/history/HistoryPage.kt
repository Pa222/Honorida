package com.honorida.activities.main.ui.components.pages.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.honorida.R
import com.honorida.activities.main.ui.components.topbar.TopBar

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
        Column(Modifier.padding(innerPadding)) {

        }
    }
}

@Preview
@Composable
private fun HistoryPagePreview() {
    HistoryPage()
}