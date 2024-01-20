package com.honorida.representation.uiStates

sealed class BookReaderState{
    data object BookLoading: BookReaderState()
    data object WaitingForPages: BookReaderState()
    data object BookLoaded: BookReaderState()
    data object Failed: BookReaderState()
}

data class BookReaderUiState(
    val bookInfo: BookInfo? = null,
    val readerState: BookReaderState = BookReaderState.BookLoading,
    val fontSize: Float = 14F,
    val pages: List<String> = emptyList(),
    val chapterTitle: String? = null
)