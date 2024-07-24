package tech.gelab.measurement.electrodetip.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.storage.pb.Settings
import tech.gelab.cardiograph.storage.pb.copy
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.measurement.electrodetip.CloseDialogFeatureEvent
import tech.gelab.measurement.electrodetip.ElectrodeConnectionFeatureEntryImpl.Companion.NEXT_DESTINATION
import tech.gelab.measurement.electrodetip.domain.usecase.GetInitialStateUseCase
import tech.gelab.measurement.electrodetip.domain.ElectrodeConnectionEvent
import tech.gelab.measurement.electrodetip.domain.ElectrodeConnectionState
import timber.log.Timber
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
                    electrodeTipShowNoMore = event.value
                }
            }
        }
        viewState = viewState.copy(checkboxSelected = event.value)
    }

    private fun onCloseClicked() {
        Timber.d("onCloseClicked: nextDestination is set to $nextDestination")
        closeDialogFeatureEventHandler.obtainEvent(CloseDialogFeatureEvent(nextDestination))
    }

    override fun obtainEvent(viewEvent: ElectrodeConnectionEvent) {
        when (viewEvent) {
            is ElectrodeConnectionEvent.CheckboxSelected -> onCheckboxSelected(viewEvent)
            ElectrodeConnectionEvent.CloseClicked -> onCloseClicked()
        }
    }
}