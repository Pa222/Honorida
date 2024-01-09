package com.honorida.data.local.repositories.interfaces

import androidx.datastore.core.DataStore
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.data.models.protoStore.ApplicationPreferences

interface IProtoDataStore {
    val appearancePreferences: DataStore<AppearancePreferences>
    val applicationPreferences: DataStore<ApplicationPreferences>
}