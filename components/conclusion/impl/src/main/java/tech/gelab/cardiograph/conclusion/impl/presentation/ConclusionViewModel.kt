package tech.gelab.cardiograph.conclusion.impl.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import tech.gelab.cardiograph.conclusion.impl.domain.ConclusionEvent
import tech.gelab.cardiograph.conclusion.impl.domain.ConclusionState
import tech.gelab.cardiograph.conclusion.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ConclusionViewModel @Inject constructor(
    getInitialStateUseCase: GetInitialStateUseCase
) : BaseViewModel<ConclusionState, Unit, ConclusionEvent>(getInitialStateUseCase.invoke()) {

    private fun onDownloadConclusionClick() {

    }

    private fun onGetConclusionClick() {

    }

    private fun onStartNewMeasure() {

    }

    override fun obtainEvent(viewEvent: ConclusionEvent) {
        when (viewEvent) {
            ConclusionEvent.DownloadConclusionClick -> onDownloadConclusionClick()
            ConclusionEvent.GetConclusionClick -> onGetConclusionClick()
            ConclusionEvent.StartNewMeasure -> onStartNewMeasure()
        }
    }
}