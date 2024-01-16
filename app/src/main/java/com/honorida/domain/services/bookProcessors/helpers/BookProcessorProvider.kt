package com.honorida.domain.services.bookProcessors.helpers

import android.app.Application
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.domain.constants.MimeTypes
import com.honorida.domain.services.bookProcessors.EpubBookProcessor
import com.honorida.domain.services.bookProcessors.interfaces.IBookProcessor
import javax.inject.Inject

class BookProcessorProvider @Inject constructor(
    private val context: Application,
    private val preferencesStoreRepository: IDataStoreRepository
) {

    fun getBookProcessorForMimeType(mimeType: String): IBookProcessor? {
        return when (mimeType) {
            MimeTypes.Epub -> EpubBookProcessor(context, preferencesStoreRepository)
            else -> null
        }
    }
}