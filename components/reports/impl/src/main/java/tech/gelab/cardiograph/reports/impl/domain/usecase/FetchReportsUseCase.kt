package tech.gelab.cardiograph.reports.impl.domain.usecase

import tech.gelab.cardiograph.reports.impl.data.ReportsDataSource
import javax.inject.Inject

class FetchReportsUseCase @Inject constructor(private val reportsDataSource: ReportsDataSource) {

    suspend fun invoke() {

    }

}