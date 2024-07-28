package tech.gelab.cardiograph.idpicker.newemployee.domain

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api

sealed interface DatePickerEvent {
    data class SubmitClick @OptIn(ExperimentalMaterial3Api::class) constructor(
        val datePickerState: DatePickerState
    ): DatePickerEvent
}