package tech.gelab.cardiograph.bridge.impl.connection

import android.bluetooth.BluetoothGatt
import android.content.Context
import no.nordicsemi.android.ble.BleManager

class CardioBleManager(context: Context) : BleManager(context),
    ConnectionStateProvider by ConnectionStateProviderDelegate() {

    init {
        super.setConnectionObserver(this)
    }



    override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
        // TODO check
        return true
    }
}