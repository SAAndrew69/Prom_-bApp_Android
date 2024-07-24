package tech.gelab.cardiograph.measurement.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.takeWhile
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.cardiograph.measurement.api.MeasurementState
import tech.gelab.cardiograph.measurement.impl.MeasurementConst
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.measurement.impl.util.getTimeLabel
import tech.gelab.cardiograph.measurement.impl.util.timeFlow
import javax.inject.Inject

class ExerciseCompleteClickUseCase @Inject constructor(
    private val measurementApi: MeasurementApi,
    private val updateBottomSheetUseCase: UpdateBottomSheetUseCase,
) {

    private fun toBottomSheetState(
        seconds: Int,
        measurementState: MeasurementState
    ): BottomSheetState? {
        return if (measurementState !is MeasurementState.Started) BottomSheetState.SecondMeasurePreparation(
            progressLabel = getTimeLabel(seconds),
            progress = seconds.toFloat() / MeasurementConst.MAX_MEASURE_SECONDS,
            runningOut = seconds < 10,
            prepFailed = seconds == 0
        ) else null
    }

    private fun bottomSheetFlow(): Flow<BottomSheetState> {
        return combine(
            timeFlow(),
            measurementApi.observeMeasurementState(),
            ::toBottomSheetState
        ).takeWhile { it != null }.filterNotNull()
    }

    suspend fun invoke() {
        bottomSheetFlow().collect(updateBottomSheetUseCase::update)
    }

}