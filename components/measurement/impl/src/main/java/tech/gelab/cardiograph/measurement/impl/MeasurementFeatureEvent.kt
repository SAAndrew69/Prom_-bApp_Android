package tech.gelab.cardiograph.measurement.impl

sealed interface MeasurementFeatureEvent {
    data object StartAgain : MeasurementFeatureEvent
}