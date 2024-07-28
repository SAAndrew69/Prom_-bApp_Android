package tech.gelab.cardiograph.idpicker.newemployee

sealed interface NewEmployeeFeatureEvent {
    data object ProceedToNext: NewEmployeeFeatureEvent
}