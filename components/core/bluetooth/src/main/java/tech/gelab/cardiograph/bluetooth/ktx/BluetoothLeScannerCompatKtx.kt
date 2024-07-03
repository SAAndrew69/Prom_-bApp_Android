package tech.gelab.cardiograph.bluetooth.ktx

import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanFilter
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings
import tech.gelab.cardiograph.bluetooth.ScanFailedException
import timber.log.Timber

fun BluetoothLeScannerCompat.scan(
    filters: List<ScanFilter> = emptyList(),
    settings: ScanSettings,
): Flow<ScanResult> {
    return callbackFlow {
        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, scanResult: ScanResult) {
                Timber.d("onScanResult: $scanResult")
                trySend(scanResult).onFailure { exc ->
                    Timber.e(exc, "Scan result failed")
                }
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
                results.forEach { scanResult ->
                    trySend(scanResult).onFailure { exc ->
                        Timber.e(exc, "Scan result failed")
                    }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                Timber.e("Scan failed with error code: $errorCode")
                cancel(
                    "Scan failed",
                    ScanFailedException(ScanFailedException.Error.fromErrorCode(errorCode))
                )
            }
        }
        startScan(filters, settings, scanCallback)
        Timber.d("Scan started")
        awaitClose {
            Timber.d("Scan stopped")
            stopScan(scanCallback)
        }
    }
}