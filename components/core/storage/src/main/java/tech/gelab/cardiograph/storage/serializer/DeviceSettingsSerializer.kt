package tech.gelab.cardiograph.storage.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import java.io.InputStream
import java.io.OutputStream

object DeviceSettingsSerializer: Serializer<DeviceSettings> {
    override val defaultValue: DeviceSettings
        get() = DeviceSettings.getDefaultInstance()

    const val DEVICE_SETTINGS_FILENAME = "deviceSettings.pb"

    override suspend fun readFrom(input: InputStream): DeviceSettings {
        return try {
            DeviceSettings.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: DeviceSettings, output: OutputStream) {
        t.writeTo(output)
    }

    val Context.deviceSettingsDataStore: DataStore<DeviceSettings> by dataStore(
        fileName = DEVICE_SETTINGS_FILENAME,
        serializer = DeviceSettingsSerializer
    )
}