package tech.gelab.cardiograph.scanner.impl.domain.model.event

import tech.gelab.cardiograph.scanner.api.ScannerApi

sealed interface ScannerScreenEvent {
    data class DeviceClick(val device: ScannerApi.DiscoveredDevice) : ScannerScreenEvent
    data object SkipClick : ScannerScreenEvent
    data object RestartScanClick : ScannerScreenEvent
    data object GoBack : ScannerScreenEvent
}