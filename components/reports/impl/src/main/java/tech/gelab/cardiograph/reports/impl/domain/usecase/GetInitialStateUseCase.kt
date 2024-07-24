package tech.gelab.cardiograph.reports.impl.domain.usecase

import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.reports.impl.domain.ReportsScreenState
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(){

    fun invoke() : ReportsScreenState {
        return ReportsScreenState(
            pullRefreshAvailable = false,
            cachedReports = persistentListOf(),
            availableReports = persistentListOf()
        )
    }

}