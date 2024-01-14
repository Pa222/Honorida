package com.honorida.ui.components.pages.library

import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.honorida.R
import com.honorida.domain.constants.MimeTypes
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.Permissions
import com.honorida.domain.services.foreground.BookParserForegroundService
import com.honorida.representation.viewModels.LibraryViewModel
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
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp

    val getContentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            Intent(context, BookParserForegroundService::class.java)
                .apply {
                    putExtra(Extras.FileUri.key, uri.toString())
                }
                .also {
                    context.startService(it)
                }
        }
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
            val requiredPermissions = mutableListOf<Permissions>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requiredPermissions.add(Permissions.PostNotifications)
            }
            FloatingActionButton(
                icon = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add_book),
                onClick = {
                    getContentLauncher.launch(MimeTypes.Epub)
                },
                requiredPermissions = requiredPermissions,
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
                        onRemove = {

                        },
                        modifier = Modifier
                            .height((screenHeight * 0.3).dp)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}