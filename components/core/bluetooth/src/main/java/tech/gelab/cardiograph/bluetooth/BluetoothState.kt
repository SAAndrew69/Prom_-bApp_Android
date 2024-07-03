package tech.gelab.cardiograph.bluetooth

import android.bluetooth.BluetoothAdapter

enum class BluetoothState {
    STATE_OFF,
    STATE_TURNING_ON,
    STATE_ON,
    STATE_TURNING_OFF,
    UNKNOWN;

    companion object {
        fun fromAdapterInt(int: Int): BluetoothState {
            return when (int) {
                BluetoothAdapter.STATE_ON -> STATE_ON
                BluetoothAdapter.STATE_OFF -> STATE_OFF
                BluetoothAdapter.STATE_TURNING_ON -> STATE_TURNING_ON
                BluetoothAdapter.STATE_TURNING_OFF -> STATE_TURNING_OFF
                else -> UNKNOWN
            }
        }
    }
}