package com.honorida.ui.components.shared.controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionControl(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    description: String? = null,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = text,
            )
        }
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Text(
                text = text,
            )
            if (description != null) {
                Text(
                    text = description,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}