package com.honorida.data.models.protoStore.serializers

import androidx.datastore.core.Serializer
import com.honorida.data.models.protoStore.AppearancePreferences
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AppearancePreferencesSerializer : Serializer<AppearancePreferences> {
    override val defaultValue: AppearancePreferences
        get() = AppearancePreferences()

    override suspend fun readFrom(input: InputStream): AppearancePreferences {
        return try {
            Json.decodeFromString(
                deserializer = AppearancePreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppearancePreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AppearancePreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}