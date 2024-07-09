package tech.gelab.cardiograph.idpicker.impl.presentation.viewmodel

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.idpicker.impl.IdentifierFeatureEvent
import tech.gelab.cardiograph.idpicker.impl.domain.PickerAction
import tech.gelab.cardiograph.idpicker.impl.domain.PickerEvent
import tech.gelab.cardiograph.idpicker.impl.domain.PickerState
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel(assistedFactory = IdentifierPickerViewModel.Factory::class)
class IdentifierPickerViewModel @AssistedInject constructor(
    @Assisted private val identifierPickerFeatureEventHandler: FeatureEventHandler<IdentifierFeatureEvent>,
    private val cardiographApi: CardiographApi
) : BaseViewModel<PickerState, PickerAction, PickerEvent>(
    PickerState()
) {
    override fun obtainEvent(viewEvent: PickerEvent) {
        when (viewEvent) {
            is PickerEvent.GroupRadioClick -> onGroupRadioClick(viewEvent)
            is PickerEvent.ListEmployeeChange -> onListEmployeeChange(viewEvent)
            is PickerEvent.ManualInputChange -> onManualInputChange(viewEvent)
            PickerEvent.NextClick -> onNextClick()
        }
    }

    private fun onGroupRadioClick(event: PickerEvent.GroupRadioClick) {
        viewState = viewState.copy(pickedGroupIndex = event.index)
    }

    private fun onListEmployeeChange(event: PickerEvent.ListEmployeeChange) {

    }

    private fun onManualInputChange(event: PickerEvent.ManualInputChange) {
        viewState = viewState.copy(userInput = event.value)
    }

    private fun onNextClick() {
        identifierPickerFeatureEventHandler.obtainEvent(IdentifierFeatureEvent.StartMeasure)
    }

    @AssistedFactory
    interface Factory {
        fun create(identifierPickerFeatureEventHandler: FeatureEventHandler<IdentifierFeatureEvent>): IdentifierPickerViewModel
    }
}