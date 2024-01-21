package com.honorida.ui.components.pages.bookReader.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ReadingProgress(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = "$currentPage/$totalPages",
        fontSize = 12.sp,
        modifier = modifier,
        textAlign = textAlign
    )
}