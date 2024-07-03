package tech.gelab.cardiograph.bluetooth

import tech.gelab.cardiograph.bluetooth.permissions.PermissionsState

data class ServicesState(
    val bluetoothState: BluetoothState,
    val locationState: LocationState,
    val permissionsState: PermissionsState,
) {
    fun isScannerReady(): Boolean {
        return bluetoothState == BluetoothState.STATE_ON &&
                locationState == LocationState.LOCATION_ENABLED &&
                permissionsState.isAllGranted()
    }

    fun isConnectionReady(): Boolean {
        return bluetoothState == BluetoothState.STATE_ON && permissionsState.isAllGranted()
    }
}
