package tech.gelab.cardiograph.bridge.impl.connection

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.MutableStateFlow

class ConnectionStateProviderDelegate : ConnectionStateProvider {

    private val connectionStateFlow = MutableStateFlow<ConnectionStateProvider.ConnectionState>(
        ConnectionStateProvider.ConnectionState.Disconnected(0)
    )

    override fun connectionStateFlow(): ConnectionStateProvider.ConnectionState {
        return connectionStateFlow.value
    }

    override fun onDeviceConnecting(device: BluetoothDevice) {
        connectionStateFlow.tryEmit(ConnectionStateProvider.ConnectionState.Connecting)
    }

    override fun onDeviceConnected(device: BluetoothDevice) {
        connectionStateFlow.tryEmit(ConnectionStateProvider.ConnectionState.Connected)
    }

    override fun onDeviceFailedToConnect(device: BluetoothDevice, reason: Int) {
        connectionStateFlow.tryEmit(ConnectionStateProvider.ConnectionState.FailedToConnect(reason))
    }

    override fun onDeviceReady(device: BluetoothDevice) {
        connectionStateFlow.tryEmit(ConnectionStateProvider.ConnectionState.Ready)
    }

    override fun onDeviceDisconnecting(device: BluetoothDevice) {
        connectionStateFlow.tryEmit(ConnectionStateProvider.ConnectionState.Disconnecting)
    }

    override fun onDeviceDisconnected(device: BluetoothDevice, reason: Int) {
        connectionStateFlow.tryEmit(ConnectionStateProvider.ConnectionState.Disconnected(reason))
    }
}