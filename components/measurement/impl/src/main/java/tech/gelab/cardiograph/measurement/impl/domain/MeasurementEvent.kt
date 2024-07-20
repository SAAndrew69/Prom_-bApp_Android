package tech.gelab.cardiograph.measurement.impl.domain

sealed interface MeasurementEvent {
    data object BackButtonClick : MeasurementEvent
    data object InfoButtonClick : MeasurementEvent
    data object StartMeasure : MeasurementEvent
    data object StartAgainClick : MeasurementEvent
}