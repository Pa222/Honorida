package com.honorida.data.models.protoStore.serializers

import androidx.datastore.core.Serializer
import com.honorida.data.models.protoStore.ReaderPreferences
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ReaderPreferencesSerializer : Serializer<ReaderPreferences> {
    override val defaultValue: ReaderPreferences
        get() = ReaderPreferences()

    override suspend fun readFrom(input: InputStream): ReaderPreferences {
        return try {
            Json.decodeFromString(
                deserializer = ReaderPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: ReaderPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ReaderPreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}