package tech.gelab.cardiograph.scanner.impl.presentation

import androidx.activity.result.ActivityResult
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import tech.gelab.cardiograph.bluetooth.BluetoothState
import tech.gelab.cardiograph.bluetooth.LocationState
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

    init {
        if (viewState == ScannerScreenState.Ready) startScan()
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
        }
    }

    private fun onDevicesCollect(devices: Iterable<ScannerApi.DiscoveredDevice>) {
        viewState = ScannerScreenState.Scanning(devices.filter {
            // TODO proper filtering
            it.name.isNotEmpty()
        }.toPersistentList())
    }

    private fun onScanStart() {
        viewState = ScannerScreenState.Scanning(persistentListOf())
    }

    private fun onScanCompletion(throwable: Throwable?) {
        Timber.e("onScanCompletion", throwable)
        viewState = if (throwable != null) {
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

    fun onBluetoothActivityResult(activityResult: ActivityResult) {

    }

    fun onPermissionActivityResult(map: Map<String, Boolean>) {

    }
}