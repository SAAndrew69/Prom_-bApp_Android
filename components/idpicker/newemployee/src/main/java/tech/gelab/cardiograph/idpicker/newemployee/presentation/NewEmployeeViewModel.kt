package tech.gelab.cardiograph.idpicker.newemployee.presentation

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.cardiograph.idpicker.newemployee.NewEmployeeFeatureEvent
import tech.gelab.cardiograph.idpicker.newemployee.domain.NewEmployeeEvent
import tech.gelab.cardiograph.idpicker.newemployee.domain.ViewState
import tech.gelab.cardiograph.idpicker.newemployee.domain.usecase.GetInitialStateUseCase
import javax.inject.Inject

@HiltViewModel
class NewEmployeeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getInitialStateUseCase: GetInitialStateUseCase,
    private val newEmployeeFeatureEventHandler: FeatureEventHandler<NewEmployeeFeatureEvent>
) : BaseViewModel<ViewState, Unit, NewEmployeeEvent>(getInitialStateUseCase.invoke(savedStateHandle)) {

    private fun onDateOfBirthClick(viewEvent: NewEmployeeEvent.DateOfBirthClick) {

    }

    private fun onGenderPick(viewEvent: NewEmployeeEvent.GenderPick) {
        viewState = viewState.copy(
            pickedGender = viewEvent.gender
        )
    }

    private fun onProceedClick() {
        newEmployeeFeatureEventHandler.obtainEvent(NewEmployeeFeatureEvent.ProceedToNext)
    }

    override fun obtainEvent(viewEvent: NewEmployeeEvent) {
        when (viewEvent) {
            is NewEmployeeEvent.DateOfBirthClick -> onDateOfBirthClick(viewEvent)
            is NewEmployeeEvent.GenderPick -> onGenderPick(viewEvent)
            NewEmployeeEvent.ProceedClick -> onProceedClick()
        }
    }
}