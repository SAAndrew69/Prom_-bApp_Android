package tech.gelab.cardiograph.idpicker.impl.domain

data class PickerState(
    val isDeviceConnected: Boolean = true,
    val pickedGroupIndex: Int? = null,
    val newEmployeeId: Int = 1234,
    val pickedListEmployee: Int? = null,
    val userInput: String = "",
    val userInputError: Boolean = false
)