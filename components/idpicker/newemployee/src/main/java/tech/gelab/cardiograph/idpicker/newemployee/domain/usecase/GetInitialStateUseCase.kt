package tech.gelab.cardiograph.idpicker.newemployee.domain.usecase

import androidx.lifecycle.SavedStateHandle
import tech.gelab.cardiograph.idpicker.newemployee.NewEmployeeFeatureEntryImpl
import tech.gelab.cardiograph.idpicker.newemployee.domain.ViewState
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor() {

    fun invoke(savedStateHandle: SavedStateHandle): ViewState {
        val id = savedStateHandle.get<Int>(NewEmployeeFeatureEntryImpl.ID_ARG)
        return ViewState(
            idLabel = "ID $id"
        )
    }

}