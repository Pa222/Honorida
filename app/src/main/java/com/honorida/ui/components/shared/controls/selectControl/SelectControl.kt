package com.honorida.ui.components.shared.controls.selectControl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.ui.components.shared.controls.selectControl.models.SelectControlValue

@Composable
fun <T> SelectControl(
    text: String,
    value: SelectControlValue<T>,
    options: List<SelectControlValue<T>>,
    onChange: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var displayDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable(onClick = {
                displayDialog = true
            }),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text
        )
        Text(
            text = value.title,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
    SelectControlDialog(
        title = text,
        value = value,
        options = options,
        display = displayDialog,
        onChange = onChange,
        onDismissRequest = {
            displayDialog = false
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun SelectControlPreview() {
    SelectControl(
        text = stringResource(R.string.dark_theme),
        value = SelectControlValue(
            title = stringResource(R.string.follow_system),
            value = DarkThemePreference.FOLLOW_SYSTEM
        ),
        options = emptyList(),
        onChange = { }
    )
}