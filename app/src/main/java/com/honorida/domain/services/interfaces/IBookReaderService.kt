package com.honorida.domain.services.interfaces

import android.net.Uri
import com.honorida.domain.services.models.ChapterInfo

interface IBookReaderService {
    fun getChapters(bookFileUri: Uri): List<ChapterInfo>

    fun getPages(
        bookFileUri: Uri,
        resourceId: String,
        pageSize: Int,
    ): List<String>

    fun getChapterTitle(
        bookFileUri: Uri,
        resourceId: String
    ): String?
}