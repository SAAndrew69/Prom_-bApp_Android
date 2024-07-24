package tech.gelab.measurement.electrodetip.domain.usecase

import androidx.lifecycle.SavedStateHandle
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.measurement.electrodetip.ElectrodeConnectionFeatureEntryImpl.Companion.SHOW_CHECKBOX
import tech.gelab.measurement.electrodetip.domain.ElectrodeConnectionState
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(private val measurementApi: MeasurementApi) {

    fun invoke(savedStateHandle: SavedStateHandle): ElectrodeConnectionState {
        val showCheckBox = savedStateHandle.get<Boolean>(SHOW_CHECKBOX)!!
        val checkboxSelected = measurementApi.shouldShowElectrodeConnectionTip().not()
        return ElectrodeConnectionState(
            showCheckbox = showCheckBox,
            checkboxSelected = checkboxSelected
        )
    }

}