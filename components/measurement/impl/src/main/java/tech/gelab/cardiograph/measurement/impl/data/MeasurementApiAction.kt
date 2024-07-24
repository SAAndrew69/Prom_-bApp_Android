package tech.gelab.cardiograph.measurement.impl.data

sealed interface MeasurementApiAction {
    data class Connect(val id: String) : MeasurementApiAction
    data object StartMeasure : MeasurementApiAction
    data object Disconnect : MeasurementApiAction
}