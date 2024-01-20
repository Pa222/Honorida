package com.honorida.representation.uiStates

import com.honorida.domain.constants.LoadingState

data class BookReaderUiState(
    val bookInfo: BookInfo? = null,
    val processState: LoadingState = LoadingState.Loading,
    val readerContent: String = ""
)