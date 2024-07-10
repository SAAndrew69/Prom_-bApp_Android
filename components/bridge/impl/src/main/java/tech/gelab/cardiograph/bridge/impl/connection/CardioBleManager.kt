package tech.gelab.cardiograph.bridge.impl.connection

import android.bluetooth.BluetoothGatt
import android.content.Context
import no.nordicsemi.android.ble.BleManager

class CardioBleManager(context: Context) : BleManager(context) {


    override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
        return true
    }
}