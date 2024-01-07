package com.honorida.ui.components.pages.more.subPages.appSettings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honorida.R

@Composable
fun SettingsCategory(
    title: String,
    modifier: Modifier = Modifier,
    settingsControls: @Composable () -> Unit = {  },
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.height(20.dp))
        settingsControls()
    }
}

@Preview
@Composable
fun SettingsCategoryPreview() {
    SettingsCategory(
        title = stringResource(R.string.theme)
    )
}