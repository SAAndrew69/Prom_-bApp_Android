package tech.gelab.cardiograph.pairing.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.bluetooth.BluetoothState
import tech.gelab.cardiograph.bluetooth.LocationState
import tech.gelab.cardiograph.bluetooth.ServicesState
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bridge.api.CardioBleScanner
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.pairing.impl.PairingFeatureEntryImpl.Companion.GO_BACK_AVAILABLE
import tech.gelab.cardiograph.pairing.impl.PairingFeatureEntryImpl.Companion.SKIP_AVAILABLE
import tech.gelab.cardiograph.pairing.impl.PairingFeatureEvent
import tech.gelab.cardiograph.pairing.impl.R
import tech.gelab.cardiograph.pairing.impl.domain.SearchAction
import tech.gelab.cardiograph.pairing.impl.domain.SearchEvent
import tech.gelab.cardiograph.pairing.impl.domain.SearchState
import tech.gelab.cardiograph.pairing.impl.domain.usecase.DeviceClickUseCase
import tech.gelab.cardiograph.pairing.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.pairing.impl.domain.usecase.PermissionsRequestResultHandler
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getInitialStateUseCase: GetInitialStateUseCase,
    private val pairingFeatureEventHandler: FeatureEventHandler<PairingFeatureEvent>,
    private val servicesStateProvider: ServicesStateProvider,
    private val permissionsRequestResultHandler: PermissionsRequestResultHandler,
    private val resourceProvider: ResourceProvider,
    private val cardiographApi: CardiographApi,
    private val deviceClickUseCase: DeviceClickUseCase
) : BaseViewModel<SearchState, SearchAction, SearchEvent>(getInitialStateUseCase.invoke()) {

    val goBackAvailable: Boolean = savedStateHandle.get<Boolean>(GO_BACK_AVAILABLE)!!
    val skipAvailable: Boolean = savedStateHandle.get<Boolean>(SKIP_AVAILABLE)!!

    init {
        servicesStateProvider.getServicesStateFlow()
            // Skip initial state
            .drop(1)
            .onEach(::onServicesState)
            .launchIn(viewModelScope)
        if (viewState is SearchState.Ready) {
            startScan()
        }
    }

    override fun obtainEvent(viewEvent: SearchEvent) {
        Timber.d("obtainEvent: $viewEvent")
        when (viewEvent) {
            is SearchEvent.DeviceClick -> onDeviceClick(viewEvent)
            SearchEvent.SkipClick -> onSkipSearchClick()
            SearchEvent.BackButtonClick -> onBackButtonClick()
            SearchEvent.RestartScanClick -> startScan()
            is SearchEvent.PermissionRequestResultReceive -> onPermissionRequestResultReceive(
                viewEvent
            )
        }
    }

    private fun onServicesState(servicesState: ServicesState) {
        Timber.d("onServicesState: $servicesState")
        if (servicesState.isScannerReady()) {
            viewState = SearchState.Scanning(persistentListOf())
            startScan()
        } else {
            viewState = SearchState.NotReady(
                servicesState.bluetoothState == BluetoothState.STATE_ON,
                servicesState.locationState == LocationState.LOCATION_ENABLED,
                servicesState.permissionsState.deniedPermissions.toTypedArray()
            )
        }
    }

    private fun onDevicesCollect(devices: Iterable<CardioBleScanner.DiscoveredDevice>) {
        viewState = SearchState.Scanning(devices.toPersistentList())
    }

    private fun onScanStart() {
        viewState = SearchState.Scanning(persistentListOf())
    }

    private fun onScanCompletion(throwable: Throwable?) {
        Timber.e("onScanCompletion", throwable)
        val servicesState = servicesStateProvider.getServicesState()
        viewState = if (!servicesState.isScannerReady()) {
            SearchState.NotReady(
                bluetoothEnabled = servicesState.bluetoothState == BluetoothState.STATE_ON,
                locationEnabled = servicesState.locationState == LocationState.LOCATION_ENABLED,
                deniedPermissions = servicesState.permissionsState.deniedPermissions.toTypedArray()
            )
        } else if (throwable != null) {
            SearchState.Stopped(throwable.message ?: "")
        } else {
            SearchState.Stopped(resourceProvider.getString(R.string.text_scan_stopped))
        }
    }

    private fun startScan() {
        cardiographApi.getScanner().scanDevices()
            .onEach(::onDevicesCollect)
            .onStart { onScanStart() }
            .catch { }
            .onCompletion { onScanCompletion(it) }
            .launchIn(viewModelScope)
    }

    private fun onDeviceClick(event: SearchEvent.DeviceClick) {
        viewModelScope.launch {
            deviceClickUseCase.invoke(event.device)
        }
    }

    private fun onSkipSearchClick() {
        pairingFeatureEventHandler.obtainEvent(PairingFeatureEvent.NavigateMainScreen)
    }

    private fun onBackButtonClick() {
        pairingFeatureEventHandler.obtainEvent(PairingFeatureEvent.PopBackStack)
    }

    private fun onPermissionRequestResultReceive(event: SearchEvent.PermissionRequestResultReceive) {
        val shouldOpenSettings = permissionsRequestResultHandler.checkShouldOpenSettings(event)
        Timber.d("onPermissionRequestResultReceive: shouldOpenSettings = $shouldOpenSettings")
        viewState = SearchState.NotReady(
            bluetoothEnabled = servicesStateProvider.getBluetoothState() == BluetoothState.STATE_ON,
            locationEnabled = servicesStateProvider.getLocationState() == LocationState.LOCATION_ENABLED,
            deniedPermissions = event.permissions.filter { !it.value }.keys.toTypedArray(),
            shouldOpenSettings = shouldOpenSettings
        )
    }
}