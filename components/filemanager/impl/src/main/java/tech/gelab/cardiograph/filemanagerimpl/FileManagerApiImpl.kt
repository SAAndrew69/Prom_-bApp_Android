package tech.gelab.cardiograph.filemanagerimpl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.gelab.cardiograph.filemanager.api.FileManagerApi
import tech.gelab.cardiograph.filemanagerimpl.edf.EDFwriter
import javax.inject.Inject

class FileManagerApiImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileManagerApi {

    override fun writeData(employeeId: Int, measureNumber: Int, numberOfSignals: Int, data: List<IntArray>) {
        val cacheDir = context.filesDir
        val writer = EDFwriter("", EDFwriter.EDFLIB_FILETYPE_BDFPLUS, numberOfSignals)

    }

}