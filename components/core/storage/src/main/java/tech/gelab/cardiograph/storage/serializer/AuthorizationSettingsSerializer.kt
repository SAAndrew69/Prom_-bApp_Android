package tech.gelab.cardiograph.storage.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import tech.gelab.cardiograph.storage.pb.AuthorizationSettings

object AuthorizationSettingsSerializer : Serializer<AuthorizationSettings> {
    override val defaultValue: AuthorizationSettings
        get() = AuthorizationSettings.getDefaultInstance()

    const val AUTHORIZATION_SETTINGS_FILENAME = "authorizationSettings.pb"

    override suspend fun readFrom(input: InputStream): AuthorizationSettings {
        return try {
            AuthorizationSettings.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: AuthorizationSettings, output: OutputStream) {
        t.writeTo(output)
    }

    val Context.authorizationSettingsDataStore: DataStore<AuthorizationSettings> by dataStore(
        fileName = AUTHORIZATION_SETTINGS_FILENAME,
        serializer = AuthorizationSettingsSerializer
    )
}