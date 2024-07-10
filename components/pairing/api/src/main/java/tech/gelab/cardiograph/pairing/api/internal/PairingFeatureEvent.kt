package tech.gelab.cardiograph.pairing.api.internal

import androidx.annotation.StringRes
import tech.gelab.cardiograph.bridge.api.CardiographDevice

sealed interface PairingFeatureEvent {
    data class DeviceConnected(val cardiographDevice : CardiographDevice) : PairingFeatureEvent
    data class ConnectionFailed(@StringRes val messageTextId: Int) : PairingFeatureEvent
    data object SkipConnection : PairingFeatureEvent
}