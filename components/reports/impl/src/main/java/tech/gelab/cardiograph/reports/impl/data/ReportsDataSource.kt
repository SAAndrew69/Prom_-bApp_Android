package tech.gelab.cardiograph.reports.impl.data

import tech.gelab.cardiograph.reports.impl.model.Report
import javax.inject.Inject

class ReportsDataSource @Inject constructor() {

    fun fetchReports(): Result<List<Report>> {
        // TODO
        return Result.success(emptyList())
    }

}