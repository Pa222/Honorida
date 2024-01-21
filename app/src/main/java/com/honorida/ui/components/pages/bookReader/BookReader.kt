package com.honorida.ui.components.pages.bookReader

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.honorida.R
import com.honorida.representation.uiStates.BookReaderState
import com.honorida.representation.viewModels.BookReaderViewModel
import com.honorida.ui.components.pages.bookReader.components.ReadingProgress
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
        mutableStateOf(true)
    }
    val readingModeInteractionSource = remember {
        MutableInteractionSource()
    }

    val configuration = LocalConfiguration.current
    val readerWidth = configuration.screenWidthDp * 0.95
    val readerHeight = configuration.screenHeightDp * 0.85
    val readerSize = Size(
        readerWidth.toFloat(),
        readerHeight.toFloat()
    )

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
        viewModel.loadPages(readerSize)
    }

    if (uiState.readerState == BookReaderState.BookLoaded) {
//        val window = context.findActivity()?.window
//
//        if (window != null) {
//            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
//            if (isReadingMode) {
//                insetsController.hide(WindowInsetsCompat.Type.statusBars())
//                insetsController.hide(WindowInsetsCompat.Type.navigationBars())
//            }
//            else {
//                insetsController.show(WindowInsetsCompat.Type.statusBars())
//                insetsController.show(WindowInsetsCompat.Type.navigationBars())
//            }
//        }

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
                visible = !isReadingMode,
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
                    description = uiState.chapterTitle,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                )
            }
            AnimatedVisibility(
                visible = !isReadingMode,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(10F),
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
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)),
                ) {
                    Column {
                        val coroutineScope = rememberCoroutineScope()
                        val steps = if (pagerState.pageCount > 3)
                            pagerState.pageCount - 3
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
                                    horizontal = 20.dp
                                )
                        )
                        ReadingProgress(
                            currentPage = if (pagerState.currentPage >= uiState.pages.size)
                                pagerState.currentPage
                            else pagerState.currentPage + 1,
                            totalPages = uiState.pages.size,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
//                    Row(
//                        horizontalArrangement = Arrangement.End,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                horizontal = 20.dp,
//                                vertical = 15.dp
//                            )
//                    ) {
//                        var showSettings by remember {
//                            mutableStateOf(false)
//                        }
//                        IconButton(
//                            onClick = {
//                                showSettings = !showSettings
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Outlined.Settings,
//                                contentDescription = stringResource(R.string.settings)
//                            )
//                        }
//                        ReaderSettings(
//                            open = showSettings,
//                            onDismissRequest = { showSettings = false }
//                        )
//                    }
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
                            fontSize = uiState.readerSettings.fontSize.sp
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
                ReadingProgress(
                    currentPage = if (pagerState.currentPage >= uiState.pages.size)
                        pagerState.currentPage
                    else pagerState.currentPage + 1,
                    totalPages = uiState.pages.size
                )
            }
        }
    }
}
