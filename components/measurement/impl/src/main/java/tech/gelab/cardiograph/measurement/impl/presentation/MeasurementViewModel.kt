package tech.gelab.cardiograph.measurement.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.measurement.impl.MeasurementFeatureEvent
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementAction
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementEvent
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementScreenState
import tech.gelab.cardiograph.measurement.impl.domain.usecase.ExerciseCompleteClickUseCase
import tech.gelab.cardiograph.measurement.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.measurement.impl.domain.usecase.ObserveSamplesUseCase
import tech.gelab.cardiograph.measurement.impl.domain.usecase.RestartClickUseCase
import tech.gelab.cardiograph.measurement.impl.domain.usecase.StartMeasureUseCase
import tech.gelab.cardiograph.measurement.impl.domain.usecase.UpdateBottomSheetUseCase
import tech.gelab.cardiograph.ui.cardiogram.CardiogramModel
import tech.gelab.cardiograph.ui.cardiogram.MultipleCardiogramValue
import tech.gelab.cardiograph.ui.cardiogram.MultipleCardiogramValueProducer
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.cardiograph.ui.topbar.TopBarState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MeasurementViewModel @Inject constructor(
    getInitialStateUseCase: GetInitialStateUseCase,
    observeSamplesUseCase: ObserveSamplesUseCase,
    private val startMeasureUseCase: StartMeasureUseCase,
    private val restartClickUseCase: RestartClickUseCase,
    private val exerciseCompleteClickUseCase: ExerciseCompleteClickUseCase,
    private val updateBottomSheetUseCase: UpdateBottomSheetUseCase,
    private val measurementFeatureEventHandler: FeatureEventHandler<MeasurementFeatureEvent>
) : BaseViewModel<MeasurementScreenState, MeasurementAction, MeasurementEvent>(
    getInitialStateUseCase.invoke()
) {

    val multipleCardiogramValueProducer = MultipleCardiogramValueProducer(listOf(
        CardiogramModel(id = "I", "I", 100),
        CardiogramModel(id = "II", "II", 100)
    ))

    init {
        observeSamplesUseCase.invoke()
            .onEach { multipleCardiogramValueProducer.produceValue(
                values = persistentListOf(
                    MultipleCardiogramValue("I", it[2].toFloat()),
                    MultipleCardiogramValue("II", it[3].toFloat())
                )
            ) }
            .launchIn(viewModelScope)

        updateBottomSheetUseCase.screenStateFlow
            .onEach(::onBottomSheetStateCollect)
            .launchIn(viewModelScope)
    }

    private fun onBottomSheetStateCollect(bottomSheetState: BottomSheetState?) {
        viewState = viewState.copy(
            topBarState = TopBarState(
                titleId = if (bottomSheetState == null) R.string.title_prepare_measure
                else R.string.title_measure,
                showBackButton = bottomSheetState == null
            ), bottomSheetState = bottomSheetState
        )
    }

    private fun onStartAgainClick() {
        Timber.d("onStartAgainClick")
        restartClickUseCase.invoke()
    }

    private fun onStartMeasureClick() {
        Timber.d("onStartMeasureClick")
        viewModelScope.launch {
            startMeasureUseCase.invoke()
        }
    }

    private fun onBackButtonClick() {
        Timber.d("onBackButtonClick")
        measurementFeatureEventHandler.obtainEvent(
            MeasurementFeatureEvent.GoBack
        )
    }

    private fun onInfoButtonClick() {
        Timber.d("onInfoButtonClick")
        measurementFeatureEventHandler.obtainEvent(
            MeasurementFeatureEvent.OpenInfoDialog
        )
    }

    private fun onExerciseCompleteClick() {
        viewModelScope.launch {
            exerciseCompleteClickUseCase.invoke()
        }
    }

    private fun onStartSecondMeasureClick() {
        viewModelScope.launch {
            startMeasureUseCase.invoke()
        }
    }

    override fun obtainEvent(viewEvent: MeasurementEvent) {
        when (viewEvent) {
            MeasurementEvent.StartAgainClick -> onStartAgainClick()
            MeasurementEvent.StartMeasure -> onStartMeasureClick()
            MeasurementEvent.BackButtonClick -> onBackButtonClick()
            MeasurementEvent.InfoButtonClick -> onInfoButtonClick()
            MeasurementEvent.ExerciseCompleteClick -> onExerciseCompleteClick()
            MeasurementEvent.StartSecondMeasureClick -> onStartSecondMeasureClick()
        }
    }
}