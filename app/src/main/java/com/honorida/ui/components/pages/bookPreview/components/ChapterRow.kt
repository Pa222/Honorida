package com.honorida.ui.components.pages.bookPreview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R

@Composable
fun ChapterRow(
    title: String,
    index: Int,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(60.dp)
            .clickable {
                onClick()
            }
    ) {
        Column {
            Text(
                stringResource(R.string.chapter_number, index + 1),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = title,
                fontSize = 12.sp,
            )
        }
    }
}