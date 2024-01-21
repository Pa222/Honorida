package com.honorida.representation.viewModels

import androidx.compose.ui.geometry.Size
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.context.HonoridaDatabase
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.data.models.protoStore.ReaderPreferences
import com.honorida.domain.constants.Extras
import com.honorida.domain.mappers.BookMapper
import com.honorida.domain.services.interfaces.IBookReaderService
import com.honorida.representation.uiStates.BookReaderState
import com.honorida.representation.uiStates.BookReaderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookReaderViewModel @Inject constructor(
    private val databaseContext: HonoridaDatabase,
    private val savedStateHandle: SavedStateHandle,
    private val bookReaderService: IBookReaderService,
    private val dataStore: IProtoDataStore
): ViewModel() {
    private val _uiState = MutableStateFlow(BookReaderUiState())

    private val _readerSettings = dataStore.readerPreferences.data.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ReaderPreferences()
    )

    val uiState = combine(_uiState, _readerSettings) {
        uiState, readerSettings ->
        uiState.copy(
            readerSettings = readerSettings,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        BookReaderUiState()
    )

    init {
        viewModelScope.launch {
            try {
                val bookId = savedStateHandle.get<Int>(Extras.BookId.key)
                    ?: throw NullPointerException()
                val book = databaseContext.booksDao.findById(bookId)
                    ?: throw NullPointerException()
                val resourceId = savedStateHandle.get<String>(Extras.BookResourceId.key)
                    ?: throw NullPointerException()
                val chapterTitle = bookReaderService.getChapterTitle(
                    book.fileUrl.toUri(),
                    resourceId
                )
                _uiState.update {
                    it.copy(
                        bookInfo = BookMapper.mapToInfoModel(book),
                        readerState = BookReaderState.WaitingForPages,
                        chapterTitle = chapterTitle
                    )
                }
            } catch(e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        readerState = BookReaderState.Failed
                    )
                }
            }
        }
    }

    fun loadPages(
        containerSize: Size
    ) {
        viewModelScope.launch {
            try {
                val state = _uiState.value
                val resourceId = savedStateHandle.get<String>(Extras.BookResourceId.key)!!

                val maxCharsHorizontal = containerSize.width / state.readerSettings.fontSize
                val maxCharsVertical = containerSize.height / state.readerSettings.fontSize
                val maxCharsPerPage = (maxCharsHorizontal * maxCharsVertical).toInt()

                val pages = bookReaderService.getPages(
                    state.bookInfo!!.fileUrl.toUri(),
                    resourceId,
                    maxCharsPerPage
                )
                _uiState.update {
                    it.copy(
                        pages = pages,
                        readerState = BookReaderState.BookLoaded
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        readerState = BookReaderState.Failed
                    )
                }
            }
        }
    }
}