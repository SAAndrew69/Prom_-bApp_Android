package tech.gelab.cardiograph.bridge.api

import kotlinx.coroutines.flow.StateFlow

interface CardiographApi {
    fun observeCardiographState() : StateFlow<CardiographState>
}