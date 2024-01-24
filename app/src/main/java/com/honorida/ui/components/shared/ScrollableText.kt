package com.honorida.ui.components.shared

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    textHeight: Dp = 200.dp
) {
    val configuration = LocalConfiguration.current
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .width((configuration.screenWidthDp * 0.3).dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .animateContentSize()
                .height(if (expanded) textHeight else 40.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
            )
        }
        if (expanded) {
            IconButton(
                onClick = {
                    expanded = false
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = stringResource(
                        R.string.expand
                    )
                )
            }
        }
        else {
            IconButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(
                        R.string.collapse
                    )
                )
            }
        }
    }
}