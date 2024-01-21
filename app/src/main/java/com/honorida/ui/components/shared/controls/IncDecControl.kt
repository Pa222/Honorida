package com.honorida.ui.components.shared.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honorida.R

@Composable
fun IncDecControl(
    title: String,
    min: Int,
    max: Int,
    step: Int,
    value: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 70.dp)
        ) {
            IconButton(
                onClick = {
                    val newValue = value - step
                    if (newValue >= min) {
                        onChange(newValue)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Remove,
                    contentDescription = stringResource(R.string.remove)
                )
            }
            TextField(
                value = value.toString(),
                onValueChange = {
                    val intValue = it.toIntOrNull()
                    if (intValue != null) {
                        var newValue = intValue
                        if (newValue > max) {
                            newValue = max
                        } else if (newValue < min) {
                            newValue = min
                        }
                        onChange(newValue)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )
            IconButton(
                onClick = {
                    val newValue = value + step
                    if (newValue <= max) {
                        onChange(newValue)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var value by remember {
        mutableIntStateOf(14)
    }
    IncDecControl(
        title = stringResource(R.string.font_size),
        min = 8,
        max = 18,
        step = 1,
        value = value,
        onChange = {
            value = it
        }
    )
}