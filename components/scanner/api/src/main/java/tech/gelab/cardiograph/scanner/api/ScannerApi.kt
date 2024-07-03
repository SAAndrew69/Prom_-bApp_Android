package tech.gelab.cardiograph.scanner.api

import kotlinx.coroutines.flow.Flow

interface ScannerApi {

    companion object {
        const val DEFAULT_SCAN_TIMEOUT = 10000L
        const val DEFAULT_FIND_TIMEOUT = 5000L
    }

    /**
     * Starts LE scan if Android device is ready
     * @return list of nearby devices
     */
    fun scanDevices(timeout: Long = DEFAULT_SCAN_TIMEOUT): Flow<Iterable<DiscoveredDevice>>

    /**
     * Tries to find device with given address in a given time
     * @return device if search was successful or null otherwise
     */
    suspend fun findDevice(address: String, timeout: Long = DEFAULT_FIND_TIMEOUT): DiscoveredDevice?

    data class DiscoveredDevice(val address: String, val name: String, val rssi: Int)
}