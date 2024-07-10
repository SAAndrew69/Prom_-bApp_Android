package tech.gelab.cardiograph.bridge.impl

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.nordicsemi.android.ble.ktx.suspend
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bridge.api.CardioBleScanner
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.bridge.api.CardiographState
import tech.gelab.cardiograph.bridge.impl.connection.CardioBleManager
import javax.inject.Provider

class CardiographApiImpl(
    private val context: Context,
    private val servicesStateProvider: ServicesStateProvider,
    private val cardioBleScanner: Provider<CardioBleScanner>,
    private val cardioBleManager: CardioBleManager
) : CardiographApi {

    private val cardiographStateFlow = MutableStateFlow(CardiographState.Disconnected)

    override fun observeCardiographState(): StateFlow<CardiographState> {
        return cardiographStateFlow.asStateFlow()
    }

    override fun getScanner(): CardioBleScanner {
        return cardioBleScanner.get()
    }

    override suspend fun establishConnection(id: String) {
        val cardioDevice = cardioBleScanner.get().findDevice(id)
        cardioBleManager.connect(cardioDevice.bluetoothDevice).suspend()
    }

}