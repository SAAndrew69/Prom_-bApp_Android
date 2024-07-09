package tech.gelab.cardiograph.idpicker.impl.domain

sealed interface PickerEvent {
    data class GroupRadioClick(val index: Int) : PickerEvent
    data class ListEmployeeChange(val index: Int) : PickerEvent
    data class ManualInputChange(val value: String) : PickerEvent
    data object NextClick : PickerEvent
}