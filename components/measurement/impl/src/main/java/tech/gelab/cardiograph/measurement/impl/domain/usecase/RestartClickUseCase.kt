package tech.gelab.cardiograph.measurement.impl.domain.usecase

import tech.gelab.cardiograph.measurement.api.MeasurementApi
import javax.inject.Inject

class RestartClickUseCase @Inject constructor(
    private val measurementApi: MeasurementApi,
    private val updateBottomSheetUseCase: UpdateBottomSheetUseCase
) {

    fun invoke() {
        // TODO cancel measurement api connection
        updateBottomSheetUseCase.update(null)
    }

}