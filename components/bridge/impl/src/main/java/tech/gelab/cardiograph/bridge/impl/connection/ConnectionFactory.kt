package tech.gelab.cardiograph.bridge.impl.connection

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import no.nordicsemi.android.ble.ktx.suspend
import tech.gelab.cardiograph.bluetooth.BluetoothState
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bridge.api.Connection
import tech.gelab.cardiograph.bridge.impl.exception.ServiceDisabledException
import javax.inject.Inject
import javax.inject.Provider

class ConnectionFactory @Inject constructor(
    private val servicesStateProvider: ServicesStateProvider,
    private val connectionComponentProvider: Provider<ConnectionComponent.Factory>
) {

    private fun <T> observeServicesException(): Flow<T> {
        return servicesStateProvider.getServicesStateFlow()
            .filter { !it.isConnectionReady() }
            .map {
                if (it.bluetoothState != BluetoothState.STATE_ON) throw ServiceDisabledException(
                    "Bluetooth has been disabled"
                ) else throw ServiceDisabledException("Permissions has been denied")
            }
            .cancellable()
    }

    private fun connectionFlow(id: String): Flow<ConnectionImpl> {
        return callbackFlow {
            val connectionComponent = connectionComponentProvider.get().create()
            val discoveredDevice = connectionComponent.scanner.findDevice(id)
            val bleManager = connectionComponent.cardioBleManager

            bleManager.connect(discoveredDevice.bluetoothDevice).suspend()
            val connection = ConnectionImpl(bleManager)

            trySend(connection).onFailure {
                bleManager.disconnect().suspend()
                throw CancellationException("Failure to send connection")
            }
            awaitClose {
                bleManager.disconnect().enqueue()
            }
        }
    }

    fun createFlow(id: String): Flow<Connection> {
        return merge(connectionFlow(id), observeServicesException())
    }

}