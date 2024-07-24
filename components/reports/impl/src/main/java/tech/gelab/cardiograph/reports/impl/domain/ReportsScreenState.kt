package tech.gelab.cardiograph.reports.impl.domain

import kotlinx.collections.immutable.ImmutableList
import tech.gelab.cardiograph.reports.impl.model.Report

data class ReportsScreenState(
    val pullRefreshAvailable: Boolean,
    val cachedReports: ImmutableList<Report>,
    val availableReports: ImmutableList<Report>
)