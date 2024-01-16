package com.honorida.ui.components.pages.library.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.honorida.data.models.db.Book
import com.honorida.ui.components.shared.BookThumbnail

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                onClick()
            }
    ) {
        BookThumbnail(book.coverImage)
    }
}
