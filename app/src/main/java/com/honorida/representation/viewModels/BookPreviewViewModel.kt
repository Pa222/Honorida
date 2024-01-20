package com.honorida.representation.viewModels

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.domain.constants.Extras
import com.honorida.domain.mappers.BookMapper
import com.honorida.domain.services.BookService
import com.honorida.domain.services.interfaces.IBookReaderService
import com.honorida.domain.services.interfaces.IBookService
import com.honorida.domain.services.models.ChapterInfo
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
    private val savedStateHandle: SavedStateHandle,
    private val bookService: IBookService,
    private val bookReaderService: IBookReaderService
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
                    val chapters = bookReaderService.getChapters(book.fileUrl.toUri())
                    _uiState.update {
                        it.copy(
                            bookInfo = BookMapper.mapToInfoModel(book),
                            chaptersList = chapters
                        )
                    }
                }
            }
        }
    }

    fun deleteBook(bookId: Int) {
        viewModelScope.launch {
            bookService.removeBook(bookId)
        }
    }
}