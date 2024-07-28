package tech.gelab.cardiograph.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = CardiographDatabase.VERSION)
abstract class CardiographDatabase : RoomDatabase() {

    companion object {
        const val NAME = "cardiograph"
        const val VERSION = 1
    }

    abstract fun employeeDao(): EmployeeDao

}