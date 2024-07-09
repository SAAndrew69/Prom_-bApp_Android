package tech.gelab.cardiograph.measurement.impl.domain

sealed interface MeasurementEvent {
    data object StartAgainClick: MeasurementEvent
}