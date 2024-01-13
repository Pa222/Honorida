package com.honorida.ui.components.pages.library.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.honorida.R
import com.honorida.data.models.db.Book

@Composable
fun AddBookDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    var bookTitle by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_book),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = bookTitle,
                    label = {
                        Text(text = stringResource(R.string.book_name))
                    },
                    onValueChange = {
                        bookTitle = it
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onConfirm(Book(title = bookTitle))
                    }
                ) {
                    Text(text = stringResource(R.string.add))
                }
            }
        }
    }
}
