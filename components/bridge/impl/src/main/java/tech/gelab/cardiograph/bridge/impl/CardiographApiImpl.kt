package tech.gelab.cardiograph.bridge.impl

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.bridge.api.CardiographState

class CardiographApiImpl(
    private val context: Context,
    private val servicesStateProvider: ServicesStateProvider,
) : CardiographApi {

    private val cardiographStateFlow = MutableStateFlow(CardiographState.Disconnected)

    override fun observeCardiographState(): StateFlow<CardiographState> {
        return cardiographStateFlow.asStateFlow()
    }

}