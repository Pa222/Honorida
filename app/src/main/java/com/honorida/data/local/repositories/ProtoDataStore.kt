package com.honorida.data.local.repositories

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.honorida.data.local.repositories.interfaces.IProtoDataStore
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.data.models.protoStore.UpdatesPreferences
import com.honorida.data.models.protoStore.constants.APPEARANCE_PREFERENCES_DATASTORE_NAME
import com.honorida.data.models.protoStore.constants.STORAGE_PREFERENCES_DATASTORE_NAME
import com.honorida.data.models.protoStore.constants.UPDATES_PREFERENCES_DATASTORE_NAME
import com.honorida.data.models.protoStore.serializers.AppearancePreferencesSerializer
import com.honorida.data.models.protoStore.serializers.UpdatesPreferencesSerializer

class ProtoDataStore(
    private val context: Application
) : IProtoDataStore {

    private val Context.appearancePreferencesStore
    by dataStore(APPEARANCE_PREFERENCES_DATASTORE_NAME, AppearancePreferencesSerializer)

    private val Context.updatesPreferencesStore
    by dataStore(UPDATES_PREFERENCES_DATASTORE_NAME, UpdatesPreferencesSerializer)

    override val appearancePreferences: DataStore<AppearancePreferences> by lazy {
        context.appearancePreferencesStore
    }

    override val updatesPreferences: DataStore<UpdatesPreferences> by lazy {
        context.updatesPreferencesStore
    }
}