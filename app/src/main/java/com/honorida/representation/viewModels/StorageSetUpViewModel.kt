package com.honorida.representation.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageSetUpViewModel @Inject constructor(
    private val preferencesRepository: IDataStoreRepository
): ViewModel() {

    fun saveStorageConfigured(location: Uri) {
        viewModelScope.launch {
            preferencesRepository.putPreference(DataStoreKey.StorageConfigured, true)
            preferencesRepository.putPreference(DataStoreKey.StorageUri, location.toString())
        }
    }
}