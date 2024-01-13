package com.honorida.ui.components.pages.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.honorida.R
import com.honorida.data.models.db.Book
import com.honorida.representation.viewModels.LibraryViewModel
import com.honorida.ui.components.pages.library.components.AddBookDialog
import com.honorida.ui.components.pages.library.components.BookCard
import com.honorida.ui.components.shared.buttons.FloatingActionButton
import com.honorida.ui.components.topbar.TopBar

@Composable
fun LibraryPage(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val books = uiState.books

    var showAddBookDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (showAddBookDialog) {
        AddBookDialog(
            onDismissRequest = {
                showAddBookDialog = false
            },
            onConfirm = {
                showAddBookDialog = false
                viewModel.putBook(it)
            }
        )
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
        floatingActionButton = {
            FloatingActionButton(
                icon = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add_book),
                onClick = {
                    showAddBookDialog = true
                },
                modifier = Modifier
                    .padding(20.dp)
                    .size(50.dp)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (books.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.empty_box),
                    contentDescription = stringResource(R.string.your_library_is_empty),
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .size(60.dp)
                )
                Text(
                    text = stringResource(R.string.your_library_is_empty),
                    fontSize = 18.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                items(books) {
                    BookCard(
                        book = it,
                        onRemove = { book ->
                           viewModel.deleteBook(book)
                        },
                        modifier = Modifier
                            .height(180.dp)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}