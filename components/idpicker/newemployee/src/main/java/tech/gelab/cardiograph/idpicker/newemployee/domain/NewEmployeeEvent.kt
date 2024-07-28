package tech.gelab.cardiograph.idpicker.newemployee.domain

import tech.gelab.cardiograph.room.Gender

sealed interface NewEmployeeEvent {
    data class GenderPick(val gender: Gender): NewEmployeeEvent
    data object DateOfBirthClick: NewEmployeeEvent
    data object ProceedClick: NewEmployeeEvent
}