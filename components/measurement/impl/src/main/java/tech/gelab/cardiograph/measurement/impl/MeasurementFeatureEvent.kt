package tech.gelab.cardiograph.measurement.impl

sealed interface MeasurementFeatureEvent {
    data object OpenInfoDialog : MeasurementFeatureEvent
    data object StartAgain : MeasurementFeatureEvent
}