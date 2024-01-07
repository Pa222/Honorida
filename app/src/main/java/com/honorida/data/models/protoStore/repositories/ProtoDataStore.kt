package com.honorida.data.models.protoStore.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.honorida.data.models.protoStore.AppearancePreferences
import com.honorida.data.models.protoStore.ApplicationPreferences
import com.honorida.data.models.protoStore.constants.APPEARANCE_PREFERENCES_DATASTORE_NAME
import com.honorida.data.models.protoStore.constants.APPLICATION_PREFERENCES_DATASTORE_NAME
import com.honorida.data.models.protoStore.serializers.AppearancePreferencesSerializer
import com.honorida.data.models.protoStore.serializers.ApplicationPreferencesSerializer

interface IProtoDataStore {
    val appearancePreferences: DataStore<AppearancePreferences>
    val applicationPreferences: DataStore<ApplicationPreferences>
}

class ProtoDataStore(
    private val context: Context
) : IProtoDataStore {

    private val Context.appearancePreferencesStore
    by dataStore(APPEARANCE_PREFERENCES_DATASTORE_NAME, AppearancePreferencesSerializer)

    private val Context.applicationPreferencesStore
    by dataStore(APPLICATION_PREFERENCES_DATASTORE_NAME, ApplicationPreferencesSerializer)

    override val appearancePreferences: DataStore<AppearancePreferences> by lazy {
        context.appearancePreferencesStore
    }

    override val applicationPreferences: DataStore<ApplicationPreferences> by lazy {
        context.applicationPreferencesStore
    }
}