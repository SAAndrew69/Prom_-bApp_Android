package tech.gelab.cardiograph.progressnavigation

sealed interface ProgressNavFeatureEvent {
    data object PopBackStack: ProgressNavFeatureEvent
}