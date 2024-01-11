package com.honorida.data.local.repositories.interfaces

import androidx.datastore.core.DataStore
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.data.models.protoStore.UpdatesPreferences

interface IProtoDataStore {
    val appearancePreferences: DataStore<AppearancePreferences>
    val updatesPreferences: DataStore<UpdatesPreferences>
}