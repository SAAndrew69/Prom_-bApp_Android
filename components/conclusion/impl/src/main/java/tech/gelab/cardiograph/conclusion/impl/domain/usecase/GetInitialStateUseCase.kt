package tech.gelab.cardiograph.conclusion.impl.domain.usecase

import tech.gelab.cardiograph.conclusion.impl.domain.ConclusionState
import tech.gelab.cardiograph.conclusion.impl.model.ButtonState
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(private val measurementApi: MeasurementApi) {

    fun invoke(): ConclusionState {
        // TODO
        return ConclusionState(loading = true, buttonState = ButtonState.NOT_VISIBLE)
    }

}