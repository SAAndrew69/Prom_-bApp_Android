package tech.gelab.cardiograph.measurement.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.measurement.impl.MeasurementFeatureEvent
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementAction
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementEvent
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementState
import tech.gelab.cardiograph.measurement.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel

@HiltViewModel(assistedFactory = MeasurementViewModel.Factory::class)
class MeasurementViewModel @AssistedInject constructor(
    getInitialStateUseCase: GetInitialStateUseCase,
    @Assisted private val measurementFeatureEventHandler: FeatureEventHandler<MeasurementFeatureEvent>
) : BaseViewModel<MeasurementState, MeasurementAction, MeasurementEvent>(getInitialStateUseCase.invoke()) {



    init {
        flow<Int> {
            var counter = 1

//            while (counter != 120) {
//                viewState = viewState.copy(
//                    progress = counter / 120f,
//                    timeString = "${String.format("%01d", counter / 60)}:${
//                        String.format(
//                            "%02d",
//                            counter % 60
//                        )
//                    }"
//                )
//                counter++
//                delay(1000)
//            }
            delay(1000)
            measurementFeatureEventHandler.obtainEvent(MeasurementFeatureEvent.StartAgain)
        }.launchIn(viewModelScope)
    }

    override fun obtainEvent(viewEvent: MeasurementEvent) {
        when (viewEvent) {
            MeasurementEvent.StartAgainClick -> measurementFeatureEventHandler.obtainEvent(
                MeasurementFeatureEvent.StartAgain
            )

            MeasurementEvent.StartMeasure -> TODO()
            MeasurementEvent.BackButtonClick -> TODO()
            MeasurementEvent.InfoButtonClick -> TODO()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(measurementFeatureEventHandler: FeatureEventHandler<MeasurementFeatureEvent>): MeasurementViewModel
    }
}