package com.honorida.ui.viewModels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.honorida.data.models.protoStore.AppearancePreferences

class AppViewModel(
    appearancePreferenceStore: DataStore<AppearancePreferences>
) : ViewModel() {
    val appearancePreferences = appearancePreferenceStore.data
}