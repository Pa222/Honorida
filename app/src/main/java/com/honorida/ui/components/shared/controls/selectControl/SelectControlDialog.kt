package com.honorida.ui.components.shared.controls.selectControl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.honorida.R
import com.honorida.data.local.enums.DarkThemePreference
import com.honorida.ui.components.shared.controls.selectControl.models.SelectControlValue

@Composable
fun <T> SelectControlDialog(
    title: String,
    value: SelectControlValue<T>,
    options: List<SelectControlValue<T>>,
    onChange: (T) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 15.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 15.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    options.forEach {
                        val interactionSource = remember {
                            MutableInteractionSource()
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    onChange(it.value)
                                },
                        ) {
                            RadioButton(
                                selected = it == value,
                                onClick = {
                                    onChange(it.value)
                                },
                                interactionSource = interactionSource
                            )
                            Text(text = it.title)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(text = stringResource(R.string.close))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectControlDialogPreview() {
    SelectControlDialog(
        title = stringResource(R.string.dark_theme),
        value = SelectControlValue(
            title = stringResource(R.string.follow_system),
            value = DarkThemePreference.FOLLOW_SYSTEM
        ),
        options = listOf(
            SelectControlValue(
                title = stringResource(R.string.follow_system),
                value = DarkThemePreference.FOLLOW_SYSTEM
            ),
            SelectControlValue(
                title = stringResource(R.string.disabled),
                value = DarkThemePreference.DISABLED
            ),
            SelectControlValue(
                title = stringResource(R.string.enabled),
                value = DarkThemePreference.ENABLED
            )
        ),
        onChange = { },
        onDismissRequest = { }
    )
}