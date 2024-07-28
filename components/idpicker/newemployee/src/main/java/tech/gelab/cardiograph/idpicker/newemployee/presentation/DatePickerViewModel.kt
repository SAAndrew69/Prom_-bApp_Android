package tech.gelab.cardiograph.idpicker.newemployee.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.cardiograph.idpicker.newemployee.NewEmployeeFeatureEvent
import tech.gelab.cardiograph.idpicker.newemployee.domain.DatePickerEvent
import javax.inject.Inject

@HiltViewModel
class DatePickerViewModel @Inject constructor(
    private val newEmployeeFeatureEvent: FeatureEventHandler<NewEmployeeFeatureEvent>
): BaseViewModel<Unit, Unit, DatePickerEvent>(Unit) {

    private fun onSubmitClick(viewEvent: DatePickerEvent.SubmitClick) {

    }

    override fun obtainEvent(viewEvent: DatePickerEvent) {
        when (viewEvent) {
            is DatePickerEvent.SubmitClick -> onSubmitClick(viewEvent)
        }
    }
}