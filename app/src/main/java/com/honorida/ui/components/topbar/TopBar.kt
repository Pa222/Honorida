package com.honorida.ui.components.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
        modifier: Modifier = Modifier,
        title: String = stringResource(R.string.header),
        navigateBackAction: (() -> Unit)? = null,
        description: String? = null,
        actions: @Composable (RowScope.() -> Unit) = {  },
    ) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Column {
                Text(
                    title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (description != null) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = description,
                        fontSize = 10.sp,
                    )
                }
            }
        },
        actions = actions,
        modifier = modifier,
        navigationIcon = {
            if (navigateBackAction != null) {
                IconButton(
                    onClick = { navigateBackAction() },
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = stringResource(R.string.go_back),
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(navigateBackAction = {})
}