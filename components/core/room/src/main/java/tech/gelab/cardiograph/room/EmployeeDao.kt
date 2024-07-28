package tech.gelab.cardiograph.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Query("SELECT id FROM ${EmployeeEntity.TABLE_NAME}")
    fun getEmployeeIds(): List<Int>

    @Query("SELECT id FROM ${EmployeeEntity.TABLE_NAME}")
    fun employeeIdListFlow(): Flow<List<Int>>

    @Query("SELECT * FROM ${EmployeeEntity.TABLE_NAME}")
    fun getEmployeeEntities(): List<EmployeeEntity>

    @Query("SELECT * FROM ${EmployeeEntity.TABLE_NAME}")
    fun getEmployeesFlow(): Flow<List<EmployeeEntity>>

    @Insert
    suspend fun insertEmployeeEntity(employeeEntity: EmployeeEntity)

}