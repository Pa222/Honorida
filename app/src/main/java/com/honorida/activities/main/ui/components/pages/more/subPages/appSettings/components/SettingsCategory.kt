package com.honorida.activities.main.ui.components.pages.more.subPages.appSettings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R
import com.honorida.activities.main.ui.components.shared.controls.CheckBoxControl

@Composable
fun SettingsCategory(
    title: String,
    modifier: Modifier = Modifier,
    displayBottomDivider: Boolean = true,
    settingsControls: @Composable () -> Unit = {  },
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        settingsControls()
        if (displayBottomDivider) {
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsCategoryPreview() {
    SettingsCategory(
        title = stringResource(R.string.theme)
    ) {
        CheckBoxControl(
            text = stringResource(R.string.check_application_updates_on_startup),
            checked = false,
            onChange = {}
        )
    }
}