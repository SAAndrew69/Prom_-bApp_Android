package tech.gelab.cardiograph.pairing.impl.domain

import kotlinx.collections.immutable.ImmutableList
import tech.gelab.cardiograph.bridge.api.CardioBleScanner

sealed interface SearchState {
    data class NotReady(
        val bluetoothEnabled: Boolean,
        val locationEnabled: Boolean,
        val deniedPermissions: Array<String>,
        /** Indicates if we should open app settings instead of permissions request */
        val shouldOpenSettings: Boolean = false
    ) : SearchState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NotReady

            if (bluetoothEnabled != other.bluetoothEnabled) return false
            if (locationEnabled != other.locationEnabled) return false
            if (!deniedPermissions.contentEquals(other.deniedPermissions)) return false
            if (shouldOpenSettings != other.shouldOpenSettings) return false

            return true
        }

        override fun hashCode(): Int {
            var result = bluetoothEnabled.hashCode()
            result = 31 * result + locationEnabled.hashCode()
            result = 31 * result + deniedPermissions.contentHashCode()
            result = 31 * result + shouldOpenSettings.hashCode()
            return result
        }
    }

    data class Scanning(val devices: ImmutableList<CardioBleScanner.DiscoveredDevice>): SearchState

    data class Stopped(val message: String): SearchState

    // TODO
    data object Ready: SearchState
}