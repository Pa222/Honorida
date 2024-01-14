package com.honorida.ui.components.pages.library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.honorida.R
import com.honorida.data.models.db.Book

@Composable
fun BookCard(
    book: Book,
    onRemove: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {  }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.cover_image),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
            IconButton(
                onClick = {
                    onRemove(book)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.DeleteForever,
                    contentDescription = stringResource(R.string.remove_book)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.18f)
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 10.dp),
            ) {
                Text(
                    text = book.title,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
