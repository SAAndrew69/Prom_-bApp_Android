package tech.gelab.cardiograph.pairing.impl.domain.usecase

import tech.gelab.cardiograph.bluetooth.BluetoothState
import tech.gelab.cardiograph.bluetooth.LocationState
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.pairing.impl.domain.SearchState
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(private val servicesStateProvider: ServicesStateProvider) {
    fun invoke(): SearchState {
        val servicesState = servicesStateProvider.getServicesState()
        return if (servicesState.isScannerReady()) {
            SearchState.Ready
        } else {
            SearchState.NotReady(
                bluetoothEnabled = servicesState.bluetoothState == BluetoothState.STATE_ON,
                locationEnabled = servicesState.locationState == LocationState.LOCATION_ENABLED,
                deniedPermissions = servicesState.permissionsState.deniedPermissions.toTypedArray()
            )
        }
    }
}