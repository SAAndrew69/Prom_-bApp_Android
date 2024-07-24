package tech.gelab.measurement.electrodetip.domain

sealed interface ElectrodeConnectionEvent {
    data class CheckboxSelected(val value: Boolean) : ElectrodeConnectionEvent
    data object CloseClicked : ElectrodeConnectionEvent
}