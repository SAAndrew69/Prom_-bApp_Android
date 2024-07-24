package tech.gelab.cardiograph.measurement.impl.data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.measurement.api.BluetoothQuality
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.cardiograph.measurement.api.MeasurementState
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import tech.gelab.cardiograph.storage.pb.Settings
import javax.inject.Inject
import kotlin.random.Random

class MeasurementApiImpl @Inject constructor(
    private val settingsDataStore: DataStore<Settings>,
    private val deviceDataStore: DataStore<DeviceSettings>,
    private val cardiographApi: CardiographApi
) : MeasurementApi {

    private val measurementScope = CoroutineScope(Dispatchers.IO + Job())
    private val measurementStateFlow = MutableStateFlow<MeasurementState>(MeasurementState.Ready)

    private val measurementApiActionSharedFlow = MutableSharedFlow<MeasurementApiAction>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private var firstMeasureComplete = false

    init {
        measurementScope.launch {
            measurementApiActionSharedFlow
                .filter { it is MeasurementApiAction.Connect || it == MeasurementApiAction.Disconnect }
                .collectLatest {
                    if (it is MeasurementApiAction.Connect) {
                        connectDevice()
                    }
                }
        }
    }

    private suspend fun connectDevice() {
        measurementApiActionSharedFlow.collect {

        }
    }

    override fun shouldShowElectrodeConnectionTip(): Boolean {
        return runBlocking {
            settingsDataStore.data.first().electrodeTipShowNoMore.not()
        }
    }

    override fun observeMeasurementState(): StateFlow<MeasurementState> {
        return measurementStateFlow
    }

    override fun observeSamples(): Flow<IntArray> {
        // TODO
        return flow {
            while (true) {
                currentCoroutineContext().ensureActive()
                emit(
                    intArrayOf(
                        Random.nextInt(),
                        Random.nextInt(),
                        Random.nextInt(),
                        Random.nextInt()
                    )
                )
                kotlinx.coroutines.delay(1000L)
            }
        }
    }

    override suspend fun initializeConnection() {
        val deviceSettings = deviceDataStore.data.first()

    }

    override fun startMeasure() {
        measurementStateFlow.tryEmit(MeasurementState.Started(true, 120, BluetoothQuality.GOOD))
    }

    override fun reset() {
        firstMeasureComplete = false
    }
}