package com.honorida.ui.components.pages.bookPreview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
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
                        20.dp,
                    )
            ) {
                Text(
                    text = book.title,
                    fontSize = 24.sp
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 5.dp)
                    )
                    Text(text = book.authors ?: stringResource(R.string.unknown))
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AutoStories,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 5.dp)
                    )
                    Text(text = book.publishers ?: stringResource(R.string.unknown))
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Language,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 5.dp)
                    )
                    Text(text = book.language?.uppercase() ?: stringResource(R.string.unknown))
                }
            }
        }
        Column {
            Text(
                text = description.toString(),
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
    }
}
