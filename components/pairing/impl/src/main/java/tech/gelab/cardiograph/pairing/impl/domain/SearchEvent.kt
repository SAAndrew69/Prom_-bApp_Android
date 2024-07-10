package tech.gelab.cardiograph.pairing.impl.domain

import tech.gelab.cardiograph.bridge.api.CardioBleScanner

sealed interface SearchEvent {
    data class DeviceClick(val device: CardioBleScanner.DiscoveredDevice) : SearchEvent
    data object SkipClick : SearchEvent
    data object RestartScanClick : SearchEvent
    data object BackButtonClick : SearchEvent
    data class PermissionRequestResultReceive(val permissions: Map<String, Boolean>) : SearchEvent
}