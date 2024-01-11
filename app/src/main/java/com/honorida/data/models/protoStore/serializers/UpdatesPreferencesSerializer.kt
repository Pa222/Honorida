package com.honorida.data.models.protoStore.serializers

import androidx.datastore.core.Serializer
import com.honorida.data.models.protoStore.UpdatesPreferences
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UpdatesPreferencesSerializer : Serializer<UpdatesPreferences> {
    override val defaultValue: UpdatesPreferences
        get() = UpdatesPreferences()

    override suspend fun readFrom(input: InputStream): UpdatesPreferences {
        return try {
            Json.decodeFromString(
                deserializer = UpdatesPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UpdatesPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UpdatesPreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}