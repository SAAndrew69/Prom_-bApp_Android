package tech.gelab.cardiograph.measurement.impl

sealed interface MeasurementFeatureEvent {
    data object GoBack : MeasurementFeatureEvent
    data object OpenInfoDialog : MeasurementFeatureEvent
    data object Restart : MeasurementFeatureEvent
}