package tech.gelab.cardiograph.bridge.impl.scanner

import no.nordicsemi.android.support.v18.scanner.ScanResult
import tech.gelab.cardiograph.bridge.api.CardioBleScanner

fun discoveredDevice(scanResult: ScanResult): CardioBleScanner.DiscoveredDevice {
    return CardioBleScanner.DiscoveredDevice(
        address = scanResult.device.address,
        name = scanResult.scanRecord?.deviceName ?: "",
        rssi = scanResult.rssi,
        bluetoothDevice = scanResult.device
    )
}