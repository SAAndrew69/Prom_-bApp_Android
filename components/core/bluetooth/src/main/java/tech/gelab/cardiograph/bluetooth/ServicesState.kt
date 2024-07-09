package tech.gelab.cardiograph.bluetooth

import tech.gelab.cardiograph.bluetooth.permissions.PermissionsState
import timber.log.Timber

data class ServicesState(
    val bluetoothState: BluetoothState,
    val locationState: LocationState,
    val permissionsState: PermissionsState,
) {

    // TODO debug
    init {
        Timber.d("services state init $this")
    }

    fun isScannerReady(): Boolean {
        return bluetoothState == BluetoothState.STATE_ON &&
                locationState == LocationState.LOCATION_ENABLED &&
                permissionsState.isAllGranted()
    }

    fun isConnectionReady(): Boolean {
        return bluetoothState == BluetoothState.STATE_ON && permissionsState.isAllGranted()
    }
}
