package tech.gelab.cardiograph.bridge.api

sealed interface CardiographState {
    data object Ready : CardiographState
    data object MeasureStarted : CardiographState
    data object Disconnected : CardiographState
}