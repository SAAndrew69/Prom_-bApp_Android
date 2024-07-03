package tech.gelab.cardiograph.scanner.impl.presentation

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import tech.gelab.cardiograph.bluetooth.BluetoothState
import tech.gelab.cardiograph.bluetooth.LocationState
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.impl.R
import tech.gelab.cardiograph.scanner.impl.domain.ScannerScreenEventHandler
import tech.gelab.cardiograph.scanner.impl.model.ScannerScreenState
import timber.log.Timber

@HiltViewModel
class ScannerScreenViewModel @AssistedInject constructor(
    @Assisted private val scannerScreenEventHandler: ScannerScreenEventHandler,
    private val servicesStateProvider: ServicesStateProvider,
    private val scannerApi: ScannerApi,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _scannerScreenStateFlow = MutableStateFlow(getInitialScreenState())
    val scannerScreenStateFlow = _scannerScreenStateFlow.asStateFlow()

    private fun getInitialScreenState(): ScannerScreenState {
        val servicesState = servicesStateProvider.getServicesState()
        return if (servicesState.isScannerReady()) {
            ScannerScreenState.Scanning(persistentListOf()).also { startScan() }
        } else {
            ScannerScreenState.NotReady(
                bluetoothEnabled = servicesState.bluetoothState == BluetoothState.STATE_ON,
                locationEnabled = servicesState.locationState == LocationState.LOCATION_ENABLED,
                deniedPermissions = servicesState.permissionsState.deniedPermissions.toTypedArray()
            )
        }
    }

    private fun onDevicesCollect(devices: Iterable<ScannerApi.DiscoveredDevice>) {
        _scannerScreenStateFlow.update {
            ScannerScreenState.Scanning(devices.toPersistentList())
        }
    }

    private fun onScanStart() {
        _scannerScreenStateFlow.update {
            ScannerScreenState.Scanning(persistentListOf())
        }
    }

    private fun onScanCompletion(throwable: Throwable?) {
        Timber.e("onScanCompletion", throwable)
        if (throwable != null) {
            _scannerScreenStateFlow.update {
                ScannerScreenState.Stopped(throwable.message ?: "")
            }
        } else {
            _scannerScreenStateFlow.update {
                ScannerScreenState.Stopped(resourceProvider.getString(R.string.text_scan_stopped))
            }
        }
    }

    fun startScan() {
        scannerApi.scanDevices()
            .onEach(::onDevicesCollect)
            .onStart { onScanStart() }
            .catch {  }
            .onCompletion { onScanCompletion(it) }
            .launchIn(viewModelScope)
    }

    fun onBluetoothActivityResult(activityResult: ActivityResult) {

    }

    fun onPermissionActivityResult(map: Map<String, Boolean>) {

    }

    @AssistedFactory
    interface Factory {
        fun create(scannerScreenEventHandler: ScannerScreenEventHandler): ScannerScreenViewModel
    }
}