package tech.gelab.cardiograph.conclusion.impl.domain

import tech.gelab.cardiograph.conclusion.impl.model.ButtonState

data class ConclusionState(
    val loading: Boolean = true,
    val buttonState: ButtonState
)
