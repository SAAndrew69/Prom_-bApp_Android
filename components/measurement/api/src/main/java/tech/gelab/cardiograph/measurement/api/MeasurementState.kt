package tech.gelab.cardiograph.measurement.api

sealed interface MeasurementState {

    data object Ready : MeasurementState

    data class Failed(val failureReason: FailureReason) : MeasurementState

    /** Measurement has been started
     *  @param firstMeasure is this a first measure in a current session
     *  @param remainedTime remained measurement time in seconds
     *  @param bluetoothQuality quality of bluetooth connection */
    data class Started(
        val firstMeasure: Boolean,
        val remainedTime: Int,
        val bluetoothQuality: BluetoothQuality
    ) : MeasurementState

    data object UploadingData : MeasurementState

    /** Data was processed with success, awaiting for second measure to start
     *  @param remainedTime time in seconds remained for second measure to start */
    data class SecondMeasureReady(val remainedTime: Int) : MeasurementState

    data object Success : MeasurementState

}