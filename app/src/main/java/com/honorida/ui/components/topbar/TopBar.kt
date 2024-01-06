package com.honorida.ui.components.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.honorida.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
        modifier: Modifier = Modifier,
        title: String = stringResource(R.string.header),
        actions: @Composable (RowScope.() -> Unit) = {  },
    ) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = actions,
        modifier = modifier
    )
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar()
}