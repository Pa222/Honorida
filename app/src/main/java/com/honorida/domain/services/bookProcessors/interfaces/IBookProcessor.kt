package com.honorida.domain.services.bookProcessors.interfaces

import android.net.Uri
import com.honorida.domain.services.bookProcessors.models.ProcessedBook

interface IBookProcessor {
    suspend fun processBook(fileUri: Uri): ProcessedBook
}