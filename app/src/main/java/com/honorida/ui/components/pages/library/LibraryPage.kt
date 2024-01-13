package com.honorida.ui.components.pages.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.honorida.R
import com.honorida.representation.viewModels.LibraryViewModel
import com.honorida.ui.components.topbar.TopBar

@Composable
fun LibraryPage(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val books = uiState.books
    books.forEach {
        println(it)
    }
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.library),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {

        }
    }
}

@Preview
@Composable
private fun LibraryPagePreview() {
    LibraryPage()
}