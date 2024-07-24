package tech.gelab.cardiograph.bridge.impl.connection

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.callback.DataReceivedCallback
import no.nordicsemi.android.ble.ktx.suspend
import timber.log.Timber
import java.util.UUID

private const val NORDIC_UART_SERVICE_UUID = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E"
private const val RX_CHARACTERISTIC_UUID = "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"
private const val TX_CHARACTERISTIC_UUID = "6E400003-B5A3-F393-E0A9-E50E24DCCA9E"

class CardioBleManager(context: Context) : BleManager(context),
    ConnectionStateProvider by ConnectionStateProviderDelegate() {

    private var uartService: BluetoothGattService? = null

    /** The peer can send data to the device by writing to the RX Characteristic of the service.
     *  ATT Write Request or ATT Write Command can be used.
     *  The received data is sent on the UART interface */
    private var rxCharacteristic: BluetoothGattCharacteristic? = null

    /** If the peer has enabled notifications for the TX Characteristic,
     *  the application can send data to the peer as notifications.
     *  The application will transmit all data received over UART as notifications. */
    private var txCharacteristic: BluetoothGattCharacteristic? = null

    init {
        super.setConnectionObserver(this)
    }

    override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
        uartService = gatt.getService(UUID.fromString(NORDIC_UART_SERVICE_UUID))
        rxCharacteristic = uartService?.getCharacteristic(UUID.fromString(RX_CHARACTERISTIC_UUID))
        txCharacteristic = uartService?.getCharacteristic(UUID.fromString(TX_CHARACTERISTIC_UUID))
        Timber.w("is required service supported: $uartService")
        return uartService != null && rxCharacteristic != null && txCharacteristic != null
    }

    suspend fun enableIndication(callback: DataReceivedCallback) {
        setIndicationCallback(rxCharacteristic).with(callback)
        enableIndications(rxCharacteristic).suspend()
    }
}