package tech.gelab.cardiograph.idpicker.impl

sealed interface IdentifierFeatureEvent {
    data object StartMeasure : IdentifierFeatureEvent
    data object ConnectDevice : IdentifierFeatureEvent
}