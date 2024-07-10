package tech.gelab.cardiograph.pairing.impl.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.pairing.impl.PairingFeatureEvent
import tech.gelab.cardiograph.pairing.impl.domain.ConnectionAction
import tech.gelab.cardiograph.pairing.impl.domain.ConnectionEvent
import tech.gelab.cardiograph.pairing.impl.domain.ConnectionState
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import timber.log.Timber

@HiltViewModel(assistedFactory = ConnectionScreenViewModel.Factory::class)
class ConnectionScreenViewModel @AssistedInject constructor(
    @Assisted private val pairingFeatureEventHandler: FeatureEventHandler<PairingFeatureEvent>,
    private val deviceSettingsDataStore: DataStore<DeviceSettings>,
    private val cardiographApi: CardiographApi
) : BaseViewModel<ConnectionState, ConnectionAction, ConnectionEvent>(ConnectionState()) {

    init {
        viewModelScope.launch {
            val deviceSettings = deviceSettingsDataStore.data.first()
            try {
                cardiographApi.establishConnection(deviceSettings.deviceId)
                pairingFeatureEventHandler.obtainEvent(PairingFeatureEvent.NavigateMainScreen)

            } catch (e: Exception) {
                Timber.e(e)
                pairingFeatureEventHandler.obtainEvent(PairingFeatureEvent.PopBackStack)
            }
        }
    }

    override fun obtainEvent(viewEvent: ConnectionEvent) {

    }

    @AssistedFactory
    interface Factory {
        fun create(pairingFeatureEventHandler: FeatureEventHandler<PairingFeatureEvent>): ConnectionScreenViewModel
    }
}