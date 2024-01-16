package com.honorida.representation.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.domain.constants.Extras
import com.honorida.representation.uiStates.BookInfo
import com.honorida.representation.uiStates.BookPreviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookPreviewViewModel @Inject constructor(
    private val databaseContext: HonoridaDatabase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(BookPreviewUiState())

    val uiState
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val bookId = savedStateHandle.get<Int>(Extras.BookId.key)
            if (bookId != null) {
                val book = databaseContext.booksDao.findById(bookId)
                if (book != null) {
                    _uiState.update {
                        it.copy(
                            bookInfo = BookInfo(
                                id = book.id,
                                fileUrl = book.fileUrl,
                                coverImage = book.coverImage,
                                language = book.language,
                                description = book.description,
                                publishers = book.publishers,
                                title = book.title,
                                authors = book.authors
                            )
                        )
                    }
                }
            }
        }
    }
}