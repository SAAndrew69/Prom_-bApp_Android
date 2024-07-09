package tech.gelab.cardiograph.measurement.impl.domain

import tech.gelab.cardiograph.measurement.impl.model.BluetoothQuality

data class MeasurementState(
    val bluetoothQuality: BluetoothQuality = BluetoothQuality.GOOD,
    val employeeId: String = "123456",
    val measurementString: String = "Первое",
    val progress: Float = 0f,
    val timeString: String = "0:00"
)