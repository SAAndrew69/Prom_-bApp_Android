package tech.gelab.cardiograph.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val employeeDao: EmployeeDao) {

    fun employeeIdListFlow(): Flow<List<EmployeeEntity>> {
        return employeeDao.getEmployeesFlow()
    }

    suspend fun insertEmployee(id: Int, gender: Gender, dateOfBirth: Long) {
        employeeDao.insertEmployeeEntity(EmployeeEntity(id, gender, dateOfBirth))
    }

}