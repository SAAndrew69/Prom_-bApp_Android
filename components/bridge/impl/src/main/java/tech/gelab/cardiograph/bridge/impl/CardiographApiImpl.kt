package tech.gelab.cardiograph.bridge.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.gelab.cardiograph.bridge.api.CardioBleScanner
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.bridge.api.CardiographState
import tech.gelab.cardiograph.bridge.api.Connection
import tech.gelab.cardiograph.bridge.impl.connection.ConnectionFactory
import javax.inject.Provider

class CardiographApiImpl(
    private val cardioBleScanner: Provider<CardioBleScanner>,
    private val connectionFactory: ConnectionFactory
) : CardiographApi {

    private val cardiographStateFlow = MutableStateFlow(CardiographState.Disconnected)

    override fun observeCardiographState(): StateFlow<CardiographState> {
        return cardiographStateFlow.asStateFlow()
    }

    override fun getScanner(): CardioBleScanner {
        return cardioBleScanner.get()
    }

    override suspend fun establishConnection(id: String): Flow<Connection> {
        return connectionFactory.createFlow(id)
    }

}