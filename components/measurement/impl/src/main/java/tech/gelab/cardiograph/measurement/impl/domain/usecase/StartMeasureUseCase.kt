package tech.gelab.cardiograph.measurement.impl.domain.usecase

import android.annotation.SuppressLint
import android.widget.Toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.takeWhile
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.cardiograph.measurement.api.MeasurementState
import tech.gelab.cardiograph.measurement.impl.MeasurementConst
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.measurement.impl.util.getTimeLabel
import tech.gelab.cardiograph.measurement.impl.util.timeFlow
import javax.inject.Inject

class StartMeasureUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val toastHelper: ToastHelper,
    private val measurementApi: MeasurementApi,
    private val updateBottomSheetUseCase: UpdateBottomSheetUseCase
) {

    private fun onBottomSheetStateCollect(bottomSheetState: BottomSheetState?) {
        if (bottomSheetState == null) {
            toastHelper.showToast(R.string.text_measure_error, Toast.LENGTH_SHORT)
        }
        updateBottomSheetUseCase.update(bottomSheetState)
    }

    private fun observeMeasureFlow(): Flow<MeasurementState> {
        return measurementApi.observeMeasurementState()
            .takeWhile { it != MeasurementState.UploadingData || it !is MeasurementState.Failed }
    }

    private fun toBottomSheetState(
        seconds: Int,
        measurementState: MeasurementState
    ): BottomSheetState? {

        return when (measurementState) {
            is MeasurementState.Started -> {
                BottomSheetState.MeasureInProgress(
                    bluetoothQuality = measurementState.bluetoothQuality,
                    employeeId = "1234",
                    measurementNumLabel = if (measurementState.firstMeasure) {
                        resourceProvider.getString(R.string.label_first_measure)
                    } else {
                        resourceProvider.getString(R.string.label_second_measure)
                    },
                    progressLabel = getTimeLabel(seconds),
                    progress = seconds.toFloat() / MeasurementConst.MAX_MEASURE_SECONDS
                )
            }

            is MeasurementState.SecondMeasureReady -> {
                BottomSheetState.UploadSuccess
            }

            else -> {
                null
            }
        }

    }

    suspend fun invoke() {
        measurementApi.startMeasure()

        combine(
            timeFlow(),
            measurementApi.observeMeasurementState(),
            ::toBottomSheetState
        ).takeWhile { it != null && it is BottomSheetState.UploadSuccess }
            .collect(::onBottomSheetStateCollect)
    }

}