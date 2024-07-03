package tech.gelab.cardiograph.scanner.impl.data

import no.nordicsemi.android.support.v18.scanner.ScanResult
import tech.gelab.cardiograph.scanner.api.ScannerApi

fun discoveredDevice(scanResult: ScanResult): ScannerApi.DiscoveredDevice {
    return ScannerApi.DiscoveredDevice(
        address = scanResult.device.address,
        name = scanResult.scanRecord?.deviceName ?: "",
        rssi = scanResult.rssi
    )
}