package com.honorida.ui.components.shared

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.honorida.R

@Composable
fun ApplicationLogo(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.logo128_128),
        contentDescription = null,
        modifier = modifier
    )
}