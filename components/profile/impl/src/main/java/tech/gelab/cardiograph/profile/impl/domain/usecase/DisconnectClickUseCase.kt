package tech.gelab.cardiograph.profile.impl.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import tech.gelab.cardiograph.storage.pb.deviceSettings
import timber.log.Timber
import javax.inject.Inject

class DisconnectClickUseCase @Inject constructor(private val deviceSettingsDataStore: DataStore<DeviceSettings>) {

    suspend fun invoke(): Boolean {
        return try {
            deviceSettingsDataStore.updateData {
                deviceSettings {
                    deviceId = ""
                    deviceName = ""
                    deviceConnectionPassed = false
                }
            }
            true
        } catch (ioe: IOException) {
            Timber.e(ioe)
            false
        }
    }

}