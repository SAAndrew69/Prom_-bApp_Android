package tech.gelab.cardiograph.scanner.impl.domain.model.event

import androidx.activity.result.ActivityResult
import tech.gelab.cardiograph.scanner.api.ScannerApi

sealed interface ScannerScreenEvent {
    data class DeviceClick(val device: ScannerApi.DiscoveredDevice) : ScannerScreenEvent
    data object SkipClick : ScannerScreenEvent
    data object RestartScanClick : ScannerScreenEvent
    data object GoBack : ScannerScreenEvent
    data class BluetoothEnableResult(val activityResult: ActivityResult) : ScannerScreenEvent
    data class PermissionsRequestResult(val result: Map<String, Boolean>) : ScannerScreenEvent
    data class LocationRequestResult(val result: ActivityResult) : ScannerScreenEvent
}