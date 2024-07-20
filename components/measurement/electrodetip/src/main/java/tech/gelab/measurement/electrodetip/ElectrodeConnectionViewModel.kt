package tech.gelab.measurement.electrodetip

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.storage.pb.Settings
import tech.gelab.cardiograph.storage.pb.copy
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.measurement.electrodetip.ElectrodeConnectionFeatureEntryImpl.Companion.NEXT_DESTINATION
import javax.inject.Inject

@HiltViewModel
class ElectrodeConnectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getInitialStateUseCase: GetInitialStateUseCase,
    private val settingsDataStore: DataStore<Settings>,
    private val closeDialogFeatureEventHandler: FeatureEventHandler<CloseDialogFeatureEvent>
) : BaseViewModel<ElectrodeConnectionState, Unit, ElectrodeConnectionEvent>(
    getInitialStateUseCase.invoke(
        savedStateHandle
    )
) {

    private val nextDestination: String? = savedStateHandle.get<String>(NEXT_DESTINATION)

    private fun onCheckboxSelected(event: ElectrodeConnectionEvent.CheckboxSelected) {
        viewModelScope.launch {
            settingsDataStore.updateData { settings ->
                settings.copy {
                    showElectrodeTip = event.value
                }
            }
        }
        viewState = viewState.copy(checkboxSelected = event.value)
    }

    private fun onCloseClicked() {
        closeDialogFeatureEventHandler.obtainEvent(CloseDialogFeatureEvent(nextDestination))
    }

    override fun obtainEvent(viewEvent: ElectrodeConnectionEvent) {
        when (viewEvent) {
            is ElectrodeConnectionEvent.CheckboxSelected -> onCheckboxSelected(viewEvent)
            ElectrodeConnectionEvent.CloseClicked -> onCloseClicked()
        }
    }
}

data class ElectrodeConnectionState(val showCheckbox: Boolean, val checkboxSelected: Boolean)

sealed interface ElectrodeConnectionEvent {
    data class CheckboxSelected(val value: Boolean) : ElectrodeConnectionEvent
    data object CloseClicked : ElectrodeConnectionEvent
}

