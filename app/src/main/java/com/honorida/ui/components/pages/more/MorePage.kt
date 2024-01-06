package com.honorida.ui.components.pages.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.honorida.R
import com.honorida.ui.theme.HonoridaTheme

@Composable
fun MorePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.logo128_128),
            contentDescription = null,
            modifier = Modifier.padding(vertical = 50.dp)
        )
        Divider()
    }
}

@Preview
@Composable
fun MorePagePreview() {
    MorePage()
}