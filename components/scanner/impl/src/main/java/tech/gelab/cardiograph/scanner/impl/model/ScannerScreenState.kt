package tech.gelab.cardiograph.scanner.impl.model

import kotlinx.collections.immutable.ImmutableList
import tech.gelab.cardiograph.scanner.api.ScannerApi

sealed interface ScannerScreenState {

    data class NotReady(
        val bluetoothEnabled: Boolean,
        val locationEnabled: Boolean,
        val deniedPermissions: Array<String>,
    ) : ScannerScreenState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NotReady

            if (bluetoothEnabled != other.bluetoothEnabled) return false
            if (locationEnabled != other.locationEnabled) return false
            return deniedPermissions.contentEquals(other.deniedPermissions)
        }

        override fun hashCode(): Int {
            var result = bluetoothEnabled.hashCode()
            result = 31 * result + locationEnabled.hashCode()
            result = 31 * result + deniedPermissions.contentHashCode()
            return result
        }
    }

    data class Scanning(
        val discoveredDevices: ImmutableList<ScannerApi.DiscoveredDevice>,
        val pickedDevice: ScannerApi.DiscoveredDevice? = null
    ) : ScannerScreenState

    data class Stopped(
        val message: String,
    ) : ScannerScreenState

}