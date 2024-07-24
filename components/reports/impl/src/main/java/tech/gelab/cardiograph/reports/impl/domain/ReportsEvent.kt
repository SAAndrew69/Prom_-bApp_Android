package tech.gelab.cardiograph.reports.impl.domain

import tech.gelab.cardiograph.reports.impl.model.Report

sealed interface ReportsEvent {
    data class DownloadClick(val report: Report): ReportsEvent
}