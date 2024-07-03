package tech.gelab.cardiograph.scanner.impl.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.shareIn
import no.nordicsemi.android.ble.exception.BluetoothDisabledException
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanFilter
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings
import tech.gelab.cardiograph.bluetooth.BluetoothState
import tech.gelab.cardiograph.bluetooth.LocationState
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bluetooth.ktx.scan
import tech.gelab.cardiograph.scanner.api.ScannerApi

class ScannerApiImpl(
    private val servicesStateProvider: ServicesStateProvider,
) : ScannerApi {

    // TODO
    private val scanFilters: List<ScanFilter> = listOf()
    private val scanSettings = ScanSettings.Builder().build()

    private fun <T> observeScannerExceptions(): Flow<T> {
        return servicesStateProvider.getServicesStateFlow()
            .filter { !it.isScannerReady() }
            .map {
                if (it.bluetoothState != BluetoothState.STATE_ON) throw BluetoothDisabledException()
                else if (it.locationState != LocationState.LOCATION_ENABLED) throw LocationDisabledException()
                else throw PermissionsException(permissions = servicesStateProvider.getPermissionsState().deniedPermissions)
            }
            .cancellable()
    }

    private fun accumulateResult(
        accumulator: MutableList<ScannerApi.DiscoveredDevice>,
        scanResult: ScanResult,
    ): MutableList<ScannerApi.DiscoveredDevice> {
        val deviceIndex = accumulator.indexOfFirst { discoveredDevice ->
            discoveredDevice.address == scanResult.device.address
        }
        if (deviceIndex == -1) {
            accumulator.add(discoveredDevice(scanResult))
        } else {
            accumulator[deviceIndex] = discoveredDevice(scanResult)
        }
        return accumulator
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun scanDevices(timeout: Long): Flow<Iterable<ScannerApi.DiscoveredDevice>> {
        return merge(
            flowOf(BluetoothLeScannerCompat.getScanner())
                .flatMapLatest { it.scan(scanFilters, scanSettings) }
                .runningFold(mutableListOf(), ::accumulateResult),
            observeScannerExceptions()
        )
    }

    override suspend fun findDevice(address: String, timeout: Long): ScannerApi.DiscoveredDevice? {
        TODO("Not yet implemented")
    }

}