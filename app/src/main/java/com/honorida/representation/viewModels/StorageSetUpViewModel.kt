package com.honorida.representation.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honorida.data.local.enums.DataStoreKey
import com.honorida.data.local.repositories.interfaces.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageSetUpViewModel @Inject constructor(
    private val preferencesRepository: IDataStoreRepository
): ViewModel() {

    fun saveStorageLocation(location: Uri) {
        viewModelScope.launch {
            preferencesRepository.putPreference(DataStoreKey.StorageUri, location.toString())
        }
    }
}