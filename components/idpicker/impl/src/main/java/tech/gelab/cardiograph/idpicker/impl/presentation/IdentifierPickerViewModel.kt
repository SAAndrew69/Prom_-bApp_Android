package tech.gelab.cardiograph.idpicker.impl.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.idpicker.impl.IdentifierFeatureEvent
import tech.gelab.cardiograph.idpicker.impl.domain.PickerAction
import tech.gelab.cardiograph.idpicker.impl.domain.PickerEvent
import tech.gelab.cardiograph.idpicker.impl.domain.PickerState
import tech.gelab.cardiograph.idpicker.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IdentifierPickerViewModel @Inject constructor(
    getInitialStateUseCase: GetInitialStateUseCase,
    private val identifierPickerFeatureEventHandler: FeatureEventHandler<IdentifierFeatureEvent>,
    private val deviceSettingsDataStore: DataStore<DeviceSettings>
) : BaseViewModel<PickerState, PickerAction, PickerEvent>(getInitialStateUseCase.invoke()) {

    init {
        deviceSettingsDataStore.data
            .onEach(::onSettingsCollect)
            .launchIn(viewModelScope)
    }

    override fun obtainEvent(viewEvent: PickerEvent) {
        when (viewEvent) {
            is PickerEvent.GroupRadioClick -> onGroupRadioClick(viewEvent)
            is PickerEvent.ListEmployeeChange -> onListEmployeeChange(viewEvent)
            is PickerEvent.ManualInputChange -> onManualInputChange(viewEvent)
            PickerEvent.NextClick -> onNextClick()
            PickerEvent.ConnectDeviceClick -> onConnectDeviceClick()
        }
    }

    private fun onSettingsCollect(settings: DeviceSettings) {
        viewState = viewState.copy(isDeviceConnected = settings.deviceConnectionPassed)
    }

    private fun onGroupRadioClick(event: PickerEvent.GroupRadioClick) {
        viewState = viewState.copy(pickedGroupIndex = event.index)
    }

    private fun onListEmployeeChange(event: PickerEvent.ListEmployeeChange) {

    }

    private fun onManualInputChange(event: PickerEvent.ManualInputChange) {
        viewState = viewState.copy(userInput = event.value)
    }

    private fun onNextClick() {
        when (viewState.pickedGroupIndex) {
            0 -> {
                identifierPickerFeatureEventHandler.obtainEvent(IdentifierFeatureEvent.CreateNewEmployeeRecord)
            }

            1 -> {
                identifierPickerFeatureEventHandler.obtainEvent(IdentifierFeatureEvent.StartMeasure)
            }

            else -> {
                Timber.e("onNextClick: next button was available while picked group index is ${viewState.pickedGroupIndex}")
            }
        }
    }

    private fun onConnectDeviceClick() {
        identifierPickerFeatureEventHandler.obtainEvent(IdentifierFeatureEvent.ConnectDevice)
    }
}