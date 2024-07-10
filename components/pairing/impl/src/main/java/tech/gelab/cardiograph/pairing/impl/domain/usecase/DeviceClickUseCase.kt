package tech.gelab.cardiograph.pairing.impl.domain.usecase

import androidx.datastore.core.DataStore
import tech.gelab.cardiograph.bridge.api.CardioBleScanner
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.pairing.impl.PairingFeatureEvent
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import tech.gelab.cardiograph.storage.pb.deviceSettings
import timber.log.Timber
import javax.inject.Inject

class DeviceClickUseCase @Inject constructor(
    private val pairingFeatureEventHandler: FeatureEventHandler<PairingFeatureEvent>,
    private val deviceSettingsDataStore: DataStore<DeviceSettings>
) {

    suspend fun invoke(discoveredDevice: CardioBleScanner.DiscoveredDevice) {
        Timber.d("deviceClicked: $discoveredDevice")
        val deviceSettings = deviceSettings {
            // TODO must be real id
            deviceId = discoveredDevice.address
            deviceName = discoveredDevice.name
        }
        deviceSettingsDataStore.updateData { deviceSettings }
        pairingFeatureEventHandler.obtainEvent(PairingFeatureEvent.NavigateConnection)
    }

}