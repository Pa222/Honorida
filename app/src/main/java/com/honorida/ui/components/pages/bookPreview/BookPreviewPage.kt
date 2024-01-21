package com.honorida.ui.components.pages.bookPreview

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.representation.viewModels.BookPreviewViewModel
import com.honorida.ui.components.navigation.Routes
import com.honorida.ui.components.navigation.getBookReaderUri
import com.honorida.ui.components.pages.bookPreview.components.BookInfoRow
import com.honorida.ui.components.pages.bookPreview.components.ChapterRow
import com.honorida.ui.components.shared.BookThumbnail

@Composable
fun BookPreviewPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BookPreviewViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val book = uiState.bookInfo
    val description = HtmlCompat.fromHtml(book.description ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 20.dp)
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = stringResource(R.string.go_back)
                )
            }
            Row {
                IconButton(onClick = {
                    menuExpanded = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(R.string.more)
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.remove_book))
                        },
                        onClick = {
                            navController.navigate(Routes.LIBRARY.route)
                            viewModel.deleteBook(book.id)
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        ) {
            BookThumbnail(
                cover = book.coverImage,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(120.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.3F),
                    )
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                    )
            ) {
                Text(
                    text = book.title,
                    fontSize = 24.sp
                )
                BookInfoRow(
                    icon = Icons.Outlined.Person,
                    text = book.authors,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                BookInfoRow(
                    icon = Icons.Outlined.AutoStories,
                    text = book.publishers,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                BookInfoRow(
                    icon = Icons.Outlined.Language,
                    text = book.language,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
        if (description.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .animateContentSize()
                    .height(200.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = description.toString(),
                    fontSize = 14.sp,
                )
            }
        }
        Text(
            text = stringResource(R.string.chapters, uiState.chaptersList.size),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.chaptersList.isNotEmpty()) {
            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                items(uiState.chaptersList) {
                    ChapterRow(
                        title = it.title,
                        index = uiState.chaptersList.indexOf(it),
                        onClick = {
                            navController.navigate(
                                getBookReaderUri(book.id, it.resourceId)
                            )
                        }
                    )
                }
            }
        }
    }
}
