package com.honorida.data.models.protoStore.serializers

import androidx.datastore.core.Serializer
import com.honorida.data.models.protoStore.ApplicationPreferences
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ApplicationPreferencesSerializer: Serializer<ApplicationPreferences> {
    override val defaultValue: ApplicationPreferences
        get() = ApplicationPreferences()

    override suspend fun readFrom(input: InputStream): ApplicationPreferences {
        return try {
            Json.decodeFromString(
                deserializer = ApplicationPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: ApplicationPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ApplicationPreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}