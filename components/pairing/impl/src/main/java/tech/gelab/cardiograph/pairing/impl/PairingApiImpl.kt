package tech.gelab.cardiograph.pairing.impl

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tech.gelab.cardiograph.pairing.api.PairingApi
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import timber.log.Timber
import javax.inject.Inject

class PairingApiImpl @Inject constructor(private val deviceSettingsDataStore: DataStore<DeviceSettings>) : PairingApi {
    override fun connectionPassed(): Boolean {
        return runBlocking {
            val deviceSettings = deviceSettingsDataStore.data.first()
            Timber.d("deviceSettings: id = ${deviceSettings.deviceId}, name = ${deviceSettings.deviceName}, passed = ${deviceSettings.deviceConnectionPassed}")
            deviceSettings.deviceConnectionPassed
        }
    }
}