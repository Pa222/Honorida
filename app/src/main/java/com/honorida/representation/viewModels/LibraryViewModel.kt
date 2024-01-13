package com.honorida.representation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.repositories.interfaces.IDaoRepository
import com.honorida.representation.uiStates.LibraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val daoRepository: IDaoRepository
): ViewModel() {

    private val _books = daoRepository.books.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )
    private val _uiState = MutableStateFlow(LibraryUiState())

    val uiState = combine(_uiState, _books) { uiState, books ->
        uiState.copy(
            books = books
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        LibraryUiState()
    )
}