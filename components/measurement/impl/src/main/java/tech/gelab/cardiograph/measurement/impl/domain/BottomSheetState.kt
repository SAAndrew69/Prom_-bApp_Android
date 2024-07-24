package tech.gelab.cardiograph.measurement.impl.domain

import tech.gelab.cardiograph.measurement.api.BluetoothQuality

sealed interface BottomSheetState {

    data class MeasureInProgress(
        val bluetoothQuality: BluetoothQuality,
        val employeeId: String,
        val measurementNumLabel: String,
        val progressLabel: String,
        val progress: Float
    ) : BottomSheetState

    data object DataUpload : BottomSheetState

    data object UploadSuccess : BottomSheetState

    data class SecondMeasurePreparation(
        val progressLabel: String,
        val progress: Float,
        val runningOut: Boolean = false,
        val prepFailed: Boolean = false
    ) : BottomSheetState

    data object MeasureSuccess: BottomSheetState

}