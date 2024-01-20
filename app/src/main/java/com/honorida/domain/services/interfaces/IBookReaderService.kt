package com.honorida.domain.services.interfaces

import android.net.Uri
import com.honorida.domain.services.models.ChapterInfo

interface IBookReaderService {
    fun getChapters(bookFileUri: Uri): List<ChapterInfo>
    fun getResourceContent(bookFileUri: Uri, resourceId: String): String
}