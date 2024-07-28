package tech.gelab.cardiograph.room

import androidx.room.Entity

@Entity(
    tableName = EmployeeEntity.TABLE_NAME,
    primaryKeys = ["id"]
)
data class EmployeeEntity(
    val id: Int,
    val gender: Gender,
    val dateOfBirth: Long
) {
    companion object {
        const val TABLE_NAME = "employee"
    }
}

enum class Gender {
    MALE,
    FEMALE
}