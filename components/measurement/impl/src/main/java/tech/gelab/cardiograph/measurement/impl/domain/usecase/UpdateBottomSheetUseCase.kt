package tech.gelab.cardiograph.measurement.impl.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import javax.inject.Inject

@ViewModelScoped
class UpdateBottomSheetUseCase @Inject constructor(){

    private val _screenStateFlow = MutableSharedFlow<BottomSheetState?>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val screenStateFlow = _screenStateFlow.asSharedFlow()

    fun update(bottomSheetState: BottomSheetState?) {
        _screenStateFlow.tryEmit(bottomSheetState)
    }

}