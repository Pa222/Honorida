package com.honorida.representation.viewModels

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.domain.constants.Extras
import com.honorida.domain.constants.LoadingState
import com.honorida.domain.mappers.BookMapper
import com.honorida.domain.services.interfaces.IBookReaderService
import com.honorida.representation.uiStates.BookReaderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookReaderViewModel @Inject constructor(
    private val databaseContext: HonoridaDatabase,
    private val savedStateHandle: SavedStateHandle,
    private val bookReaderService: IBookReaderService
): ViewModel() {
    private val _uiState = MutableStateFlow(BookReaderUiState())

    val uiState
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val bookId = savedStateHandle.get<Int>(Extras.BookId.key)
                    ?: throw NullPointerException()
                val resourceId = savedStateHandle.get<String>(Extras.BookResourceId.key)
                    ?: throw NullPointerException()
                val book = databaseContext.booksDao.findById(bookId)
                    ?: throw NullPointerException()
                val readerContent = bookReaderService.getResourceContent(
                    book.fileUrl.toUri(),
                    resourceId
                )
                _uiState.update {
                    it.copy(
                        bookInfo = BookMapper.mapToInfoModel(book),
                        processState = LoadingState.Completed,
                        readerContent = readerContent
                    )
                }
            } catch(e: Exception) {
                _uiState.update {
                    it.copy(
                        processState = LoadingState.Failed
                    )
                }
            }
        }
    }
}