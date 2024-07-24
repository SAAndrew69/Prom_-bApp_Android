package tech.gelab.cardiograph.filemanager.api

interface FileManagerApi {

    fun writeData(employeeId: Int, measureNumber: Int, numberOfSignals: Int, data: List<IntArray>)

}