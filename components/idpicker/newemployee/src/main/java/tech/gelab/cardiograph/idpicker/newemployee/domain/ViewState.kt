package tech.gelab.cardiograph.idpicker.newemployee.domain

import tech.gelab.cardiograph.room.Gender

data class ViewState(
    val idLabel: String,
    val pickedGender: Gender = Gender.MALE,
    val dateOfBirthString: String? = null,
    val proceedEnabled: Boolean = false
)