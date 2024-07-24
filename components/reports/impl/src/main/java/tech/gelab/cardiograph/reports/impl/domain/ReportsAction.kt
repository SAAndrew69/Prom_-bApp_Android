package tech.gelab.cardiograph.reports.impl.domain

sealed interface ReportsAction {
    data object FetchingReports: ReportsAction
}