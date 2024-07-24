package tech.gelab.cardiograph.measurement.impl.domain.usecase

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import javax.inject.Inject

class ObserveSamplesUseCase @Inject constructor(private val measurementApi: MeasurementApi) {

    fun invoke(): Flow<IntArray> {
        return measurementApi.observeSamples()
    }

}