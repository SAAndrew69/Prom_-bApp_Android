package tech.gelab.cardiograph.scanner.impl.presentation

import androidx.activity.result.ActivityResult
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
import tech.gelab.cardiograph.bluetooth.BluetoothState
import tech.gelab.cardiograph.bluetooth.LocationState
import tech.gelab.cardiograph.bluetooth.ServicesState
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.impl.R
import tech.gelab.cardiograph.scanner.impl.domain.model.action.ScannerScreenAction
import tech.gelab.cardiograph.scanner.impl.domain.model.event.ScannerScreenEvent
import tech.gelab.cardiograph.scanner.impl.domain.model.state.ScannerScreenState
import tech.gelab.cardiograph.scanner.impl.domain.usecase.DeviceClickUseCase
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScannerScreenViewModel @Inject constructor(
    private val servicesStateProvider: ServicesStateProvider,
    private val scannerApi: ScannerApi,
    private val resourceProvider: ResourceProvider,
    private val deviceClickUseCase: DeviceClickUseCase,
) : BaseViewModel<ScannerScreenState, ScannerScreenAction, ScannerScreenEvent>(
    getInitialScreenState(
        servicesStateProvider
    )
) {

    companion object {
        private fun getInitialScreenState(servicesStateProvider: ServicesStateProvider): ScannerScreenState {
            val servicesState = servicesStateProvider.getServicesState()
            return if (servicesState.isScannerReady()) {
                ScannerScreenState.Ready
            } else {
                ScannerScreenState.NotReady(
                    bluetoothEnabled = servicesState.bluetoothState == BluetoothState.STATE_ON,
                    locationEnabled = servicesState.locationState == LocationState.LOCATION_ENABLED,
                    deniedPermissions = servicesState.permissionsState.deniedPermissions.toTypedArray()
                )
            }
        }
    }

    private var deniedCounter = 0

    init {
        servicesStateProvider.getServicesStateFlow()
            // TODO initial state
            .drop(1)
            .onEach(::onServicesState)
            .launchIn(viewModelScope)
        if (viewState is ScannerScreenState.Ready) {
            startScan()
        }
    }

    override fun obtainEvent(viewEvent: ScannerScreenEvent) {
        Timber.d("obtainEvent: $viewEvent")
        when (viewEvent) {
            is ScannerScreenEvent.DeviceClick -> deviceClickUseCase.invoke(
                viewModelScope,
                viewEvent.device
            )

            ScannerScreenEvent.SkipClick -> viewAction = ScannerScreenAction.SkipDeviceSearch
            ScannerScreenEvent.GoBack -> viewAction = ScannerScreenAction.GoBack
            ScannerScreenEvent.RestartScanClick -> startScan()
            is ScannerScreenEvent.BluetoothEnableResult -> onBluetoothEnableResult(viewEvent)
            is ScannerScreenEvent.PermissionsRequestResult -> onPermissionActivityResult(viewEvent)
            is ScannerScreenEvent.LocationRequestResult -> onLocationActivityResult(viewEvent)
        }
    }

    private fun onServicesState(servicesState: ServicesState) {
        Timber.d("onServicesState: $servicesState")
        if (servicesState.isScannerReady()) {
            viewState = ScannerScreenState.Scanning(persistentListOf())
            startScan()
        } else {
            viewState = ScannerScreenState.NotReady(
                servicesState.bluetoothState == BluetoothState.STATE_ON,
                servicesState.locationState == LocationState.LOCATION_ENABLED,
                servicesState.permissionsState.deniedPermissions.toTypedArray()
            )
        }
    }

    private fun onDevicesCollect(devices: Iterable<ScannerApi.DiscoveredDevice>) {
        viewState = ScannerScreenState.Scanning(devices.toPersistentList())
    }

    private fun onScanStart() {
        viewState = ScannerScreenState.Scanning(persistentListOf())
    }

    private fun onScanCompletion(throwable: Throwable?) {
        Timber.e("onScanCompletion", throwable)
        val servicesState = servicesStateProvider.getServicesState()
        viewState = if (!servicesState.isScannerReady()) {
            ScannerScreenState.NotReady(
                bluetoothEnabled = servicesState.bluetoothState == BluetoothState.STATE_ON,
                locationEnabled = servicesState.locationState == LocationState.LOCATION_ENABLED,
                deniedPermissions = servicesState.permissionsState.deniedPermissions.toTypedArray()
            )
        } else if (throwable != null) {
            ScannerScreenState.Stopped(throwable.message ?: "")
        } else {
            ScannerScreenState.Stopped(resourceProvider.getString(R.string.text_scan_stopped))
        }
    }

    private fun startScan() {
        scannerApi.scanDevices()
            .onEach(::onDevicesCollect)
            .onStart { onScanStart() }
            .catch { }
            .onCompletion { onScanCompletion(it) }
            .launchIn(viewModelScope)
    }

    private fun onBluetoothEnableResult(event: ScannerScreenEvent.BluetoothEnableResult) {
        Timber.d("onBluetoothEnableResult: ${event.activityResult}")
    }

    private fun onPermissionActivityResult(event: ScannerScreenEvent.PermissionsRequestResult) {
        Timber.d("onPermissionActivityResult: ${event.result}")
        val deniedPermissions = event.result.entries.filter { !it.value }.map { it.key }
        if (deniedPermissions.isNotEmpty()) {
            deniedCounter++
        }
        if (deniedCounter >= 2) {
            // TODO make cleaner
            Timber.d("User denied permissions twice")
            viewState = ScannerScreenState.NotReady(
                bluetoothEnabled = servicesStateProvider.getBluetoothState() == BluetoothState.STATE_ON,
                locationEnabled = servicesStateProvider.getLocationState() == LocationState.LOCATION_ENABLED,
                deniedPermissions = deniedPermissions.toTypedArray(),
                shouldOpenSettings = true
            )
        }
    }

    private fun onLocationActivityResult(event: ScannerScreenEvent.LocationRequestResult) {

    }
}