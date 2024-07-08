package tech.gelab.cardiograph.scanner.api.internal

import androidx.annotation.StringRes
import tech.gelab.cardiograph.bridge.api.CardiographDevice

sealed interface ScannerFeatureEvent {
    data class DeviceConnected(val cardiographDevice : CardiographDevice) : ScannerFeatureEvent
    data class ConnectionFailed(@StringRes val messageTextId: Int) : ScannerFeatureEvent
    data object SkipConnection : ScannerFeatureEvent
}