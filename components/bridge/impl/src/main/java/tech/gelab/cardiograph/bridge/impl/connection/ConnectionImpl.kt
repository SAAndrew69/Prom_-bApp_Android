package tech.gelab.cardiograph.bridge.impl.connection

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import no.nordicsemi.android.ble.callback.DataReceivedCallback
import no.nordicsemi.android.ble.data.Data
import no.nordicsemi.android.ble.ktx.suspend
import tech.gelab.cardiograph.bridge.api.Connection
import tech.gelab.cardiograph.bridge.api.model.AdsFlow
import tech.gelab.cardiograph.bridge.api.model.EventTime
import tech.gelab.cardiograph.bridge.api.model.Packet
import tech.gelab.cardiograph.bridge.impl.parser.DataBuffer
import tech.gelab.cardiograph.bridge.impl.parser.PacketBuilder
import tech.gelab.cardiograph.bridge.impl.parser.toHexString
import timber.log.Timber

class ConnectionImpl(private val bleManager: CardioBleManager) : Connection, DataReceivedCallback {

    private val connectionScope = CoroutineScope(Dispatchers.IO + Job())

    private val dataBuffer = DataBuffer(0)

    private val packetBuilder = PacketBuilder()

    private val uartChannel = Channel<ByteArray>(
        capacity = Channel.UNLIMITED,
        onUndeliveredElement = {
            Timber.e("Element was not delivered to uart channel: ${it.toHexString()}")
        })

    private val uartSharedFlow = MutableSharedFlow<ByteArray>(
        replay = 0,
        // todo tune
        extraBufferCapacity = 100,
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

    fun tryFindPacket(dataBuffer: DataBuffer): Packet? {
        while (!packetBuilder.isKrduSet() && dataBuffer.available() < 4) {
            packetBuilder.trySetKrdu(dataBuffer.getUInt32())
        }

        try {
            if (!packetBuilder.isKrduSet()) {
                // TODO
            }
            if (packetBuilder.flags == null) {
                packetBuilder.flags = dataBuffer.getUInt8()
            }
            if (packetBuilder.logVer == null) {
                packetBuilder.logVer = dataBuffer.getUInt8()
            }
            if (packetBuilder.getPayloadCapacity() == 0) {
                val payloadSize = dataBuffer.getUInt16()
                packetBuilder.allocateBuffer(payloadSize)
            }
            while (dataBuffer.position != dataBuffer.size) {
                packetBuilder.setPayload(dataBuffer.getInt8())
            }
            packetBuilder.checkSum = dataBuffer.getUInt16()
            // TODO clear buffer
            if (packetBuilder.checkIntegrity()) {
                return packetBuilder.build()
            } else {
                packetBuilder.reset()
                return null
            }

        } catch (iae: IllegalArgumentException) {
            Timber.e("buffer has been ended")
            return null
        }
    }

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
            uartChannel.trySend(mtu)
        }
    }

    suspend fun initialize() {
        bleManager.enableIndication(this)
        connectionScope.launch {
            while (true) {
                ensureActive()
                val data = uartChannel.receive()
                for (b in data) {
                    dataBuffer.setUInt8(b)
                }
                val packet = tryFindPacket(dataBuffer)
                if (packet != null) {
                    when (packet) {
                        is Packet.AdsFlowPacket -> adsFlowSharedFlow.emit(packet.payload)
                        is Packet.EventTimePacket -> eventTimeSharedFlow.emit(packet.payload)
                    }
                }
            }
        }
    }

    fun disconnect() {
        connectionScope.launch {
            bleManager.disconnect().suspend()
            connectionScope.cancel()
        }
    }
}