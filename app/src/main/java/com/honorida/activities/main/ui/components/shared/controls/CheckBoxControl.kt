package com.honorida.activities.main.ui.components.shared.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R

@Composable
fun CheckBoxControl(
    text: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = text,
            )
            if (description != null) {
                Text(
                    text = description,
                    fontSize = 10.sp,
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckBoxControlPreview() {
    CheckBoxControl(
        text = stringResource(R.string.check_application_updates_on_startup),
        description = "Example description",
        checked = false,
        onChange = {}
    )
}