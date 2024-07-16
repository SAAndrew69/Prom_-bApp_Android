package tech.gelab.cardiograph.bridge.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CardiographApi {
    fun observeCardiographState() : StateFlow<CardiographState>

    fun getScanner() : CardioBleScanner

    suspend fun establishConnection(id: String): Flow<Connection>

}