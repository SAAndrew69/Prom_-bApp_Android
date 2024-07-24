package tech.gelab.cardiograph.measurement.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MeasurementApi {

    fun shouldShowElectrodeConnectionTip(): Boolean

    fun observeMeasurementState(): StateFlow<MeasurementState>

    fun observeSamples(): Flow<IntArray>

    suspend fun initializeConnection()

    fun startMeasure()

    fun reset()

}