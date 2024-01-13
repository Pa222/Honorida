package com.honorida.representation.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.R
import com.honorida.data.local.repositories.interfaces.IDaoRepository
import com.honorida.data.models.db.Book
import com.honorida.representation.uiStates.LibraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val daoRepository: IDaoRepository,
    private val context: Application
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

    fun putBook(book: Book) {
        viewModelScope.launch {
            try {
                daoRepository.books.putBook(book)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    context.getString(R.string.failed_to_add_book),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            try {
                daoRepository.books.deleteBook(book)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    context.getString(R.string.failed_to_remove_book),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}