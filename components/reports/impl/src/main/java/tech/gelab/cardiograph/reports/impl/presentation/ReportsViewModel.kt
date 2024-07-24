package tech.gelab.cardiograph.reports.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.reports.impl.domain.ReportsAction
import tech.gelab.cardiograph.reports.impl.domain.ReportsEvent
import tech.gelab.cardiograph.reports.impl.domain.ReportsScreenState
import tech.gelab.cardiograph.reports.impl.domain.usecase.DownloadReportUseCase
import tech.gelab.cardiograph.reports.impl.domain.usecase.FetchReportsUseCase
import tech.gelab.cardiograph.reports.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    getInitialStateUseCase: GetInitialStateUseCase,
    private val fetchReportsUseCase: FetchReportsUseCase,
    private val downloadReportUseCase: DownloadReportUseCase
) : BaseViewModel<ReportsScreenState, ReportsAction, ReportsEvent>(getInitialStateUseCase.invoke()) {

    private fun onDownloadClick(event: ReportsEvent.DownloadClick) {
        viewModelScope.launch {
            downloadReportUseCase.invoke(event.report)
        }
    }

    override fun obtainEvent(viewEvent: ReportsEvent) {
        when (viewEvent) {
            is ReportsEvent.DownloadClick -> onDownloadClick(viewEvent)
        }
    }
}