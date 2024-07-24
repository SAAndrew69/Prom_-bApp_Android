package tech.gelab.cardiograph.reports.impl.domain.usecase

import tech.gelab.cardiograph.reports.impl.data.ReportsDataSource
import tech.gelab.cardiograph.reports.impl.model.Report
import javax.inject.Inject

class DownloadReportUseCase @Inject constructor(
    private val reportsDataSource: ReportsDataSource
) {
    suspend fun invoke(report: Report) {

    }
}