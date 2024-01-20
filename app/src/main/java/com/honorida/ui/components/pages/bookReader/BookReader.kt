package com.honorida.ui.components.pages.bookReader

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.honorida.R
import com.honorida.domain.constants.LoadingState
import com.honorida.representation.viewModels.BookReaderViewModel
import com.honorida.ui.components.topbar.TopBar

@Composable
fun BookReader(
    navController: NavController,
    viewModel: BookReaderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    if (uiState.processState == LoadingState.Failed) {
        Toast.makeText(
            context,
            stringResource(R.string.failed_to_open_book),
            Toast.LENGTH_LONG
        ).show()
        navController.navigateUp()
    }
    var isReadingMode by remember {
        mutableStateOf(false)
    }
    val readingModeInteractionSource = remember {
        MutableInteractionSource()
    }
    val configuration = LocalConfiguration.current
    val readerWidth = configuration.screenWidthDp * 0.95
    val readerHeight = configuration.screenHeightDp * 0.8
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
                Text(text = "Footer")
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .height(readerHeight.dp)
                .width(readerWidth.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = uiState.readerContent
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.processState == LoadingState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BookReader(navController = rememberNavController())
}