package tech.gelab.cardiograph.bridge.impl.connection

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import no.nordicsemi.android.ble.callback.DataReceivedCallback
import no.nordicsemi.android.ble.data.Data
import no.nordicsemi.android.ble.ktx.suspend
import tech.gelab.cardiograph.bridge.api.Connection
import tech.gelab.cardiograph.bridge.api.model.AdsFlow
import tech.gelab.cardiograph.bridge.api.model.EventTime
import timber.log.Timber

class ConnectionImpl(private val bleManager: CardioBleManager) : Connection, DataReceivedCallback {

    private val connectionScope = CoroutineScope(Dispatchers.IO + Job())

    private val uartSharedFlow = MutableSharedFlow<ByteArray>(
        replay = 0,
        extraBufferCapacity = 5,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val eventTimeSharedFlow = MutableSharedFlow<EventTime>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val adsFlowSharedFlow = MutableSharedFlow<AdsFlow>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun getLastEventTime(): EventTime? {
        return try {
            eventTimeSharedFlow.replayCache[0]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    override fun getLastAdsFlow(): AdsFlow? {
        return try {
            adsFlowSharedFlow.replayCache[0]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    override fun eventTimeFlow(): Flow<EventTime> {
        return eventTimeSharedFlow
    }

    override fun adsFlow(): Flow<AdsFlow> {
        return adsFlowSharedFlow
    }

    override fun onDataReceived(device: BluetoothDevice, data: Data) {
        Timber.d("onDataReceived: ${data.value}")
        data.value?.let { mtu ->
            uartSharedFlow.tryEmit(mtu)
        }
    }

    suspend fun initialize() {
        bleManager.enableIndication(this)
        connectionScope.launch {

        }
    }

    fun disconnect() {
        connectionScope.launch {
            bleManager.disconnect().suspend()
            connectionScope.cancel()
        }
    }
}