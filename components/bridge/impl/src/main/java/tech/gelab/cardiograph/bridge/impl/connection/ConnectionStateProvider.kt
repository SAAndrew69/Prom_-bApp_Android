package tech.gelab.cardiograph.bridge.impl.connection

import no.nordicsemi.android.ble.observer.ConnectionObserver

interface ConnectionStateProvider: ConnectionObserver {

    fun connectionStateFlow(): ConnectionState

    sealed interface ConnectionState {
        data object Connecting: ConnectionState
        data object Connected: ConnectionState
        data object Ready: ConnectionState
        data class FailedToConnect(val reason: Int): ConnectionState
        data class Disconnected(val reason: Int): ConnectionState
        data object Disconnecting: ConnectionState

    }
}