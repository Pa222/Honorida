package com.honorida.ui.components.pages.bookReader

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.representation.uiStates.BookReaderState
import com.honorida.representation.viewModels.BookReaderViewModel
import com.honorida.ui.components.topbar.TopBar
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookReader(
    navController: NavController,
    viewModel: BookReaderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    var isReadingMode by remember {
        mutableStateOf(false)
    }
    val readingModeInteractionSource = remember {
        MutableInteractionSource()
    }

    val configuration = LocalConfiguration.current
    val readerWidth = configuration.screenWidthDp * 0.95
    val readerHeight = configuration.screenHeightDp * 0.8

    if (uiState.readerState == BookReaderState.Failed) {
        Toast.makeText(
            context,
            stringResource(R.string.failed_to_open_book),
            Toast.LENGTH_LONG
        ).show()
        navController.navigateUp()
    }

    if (uiState.readerState != BookReaderState.BookLoaded) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (uiState.readerState == BookReaderState.WaitingForPages) {
        viewModel.loadPages(
            Size(
                readerWidth.toFloat(),
                readerHeight.toFloat()
            )
        )
    }

    if (uiState.readerState == BookReaderState.BookLoaded) {
        val pagerState = rememberPagerState(
            pageCount = {
                uiState.pages.size + 1
            },
        )
        val bookInfo = uiState.bookInfo!!

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = isReadingMode,
                modifier = Modifier
                    .align(Alignment.TopCenter),
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                TopBar(
                    navigateBackAction = {
                        navController.navigateUp()
                    },
                    title = bookInfo.title,
                    description = uiState.chapterTitle
                )
            }
            AnimatedVisibility(
                visible = isReadingMode,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                enter = slideInVertically(
                    initialOffsetY = {
                        it
                    }
                ),
                exit = slideOutVertically(
                    targetOffsetY = {
                        it
                    }
                )
            ) {
                Row {
                    val coroutineScope = rememberCoroutineScope()
                    val steps = if (pagerState.pageCount > 2)
                                    pagerState.pageCount - 2
                                else
                                    0
                    val range = if (pagerState.pageCount > 2)
                                    0F..(pagerState.pageCount - 2).toFloat()
                                else
                                    0F..1F
                    Slider(
                        value = pagerState.currentPage.toFloat(),
                        onValueChange = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(it.roundToInt())
                            }
                        },
                        steps = steps,
                        valueRange = range,
                        modifier = Modifier
                            .padding(
                                vertical = 15.dp,
                                horizontal = 20.dp
                            )
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(0.dp),
                pageSpacing = 0.dp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(readerHeight.dp)
                    .width(readerWidth.dp)
                    .clickable(
                        interactionSource = readingModeInteractionSource,
                        indication = null
                    ) {
                        isReadingMode = !isReadingMode
                    }
            ) {
                if (it != uiState.pages.size) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = uiState.pages[it],
                            fontSize = uiState.fontSize.sp
                        )
                    }
                }
                else {
                    Text(
                        text = "Next chapter. It'll work someday",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "${
                                if (pagerState.currentPage >= uiState.pages.size) 
                                    pagerState.currentPage 
                                else pagerState.currentPage + 1
                            }" + "/" + "${uiState.pages.size}",
                    fontSize = 12.sp
                )
            }
        }
    }
}
