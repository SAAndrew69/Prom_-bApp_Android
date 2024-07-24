package tech.gelab.cardiograph.ui.ktx.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel <State, Action, Event>(initialState: State): ViewModel() {

    private val _viewStates = MutableStateFlow(initialState)
    private val _viewActions = MutableSharedFlow<Action?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    protected var viewState: State
        get() = _viewStates.value
        set(value) {
            _viewStates.value = value
        }

    protected var viewAction: Action?
        get() = _viewActions.replayCache.last()
        set(value) {
            _viewActions.tryEmit(value)
        }

    fun viewStates(): StateFlow<State> {
        return _viewStates
    }

    fun viewActions(): SharedFlow<Action?> {
        return _viewActions
    }

    abstract fun obtainEvent(viewEvent: Event)

    fun clearAction() {
        viewAction = null
    }

}