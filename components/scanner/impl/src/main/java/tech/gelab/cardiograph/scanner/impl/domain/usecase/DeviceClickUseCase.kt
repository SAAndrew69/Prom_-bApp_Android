package tech.gelab.cardiograph.scanner.impl.domain.usecase

import kotlinx.coroutines.CoroutineScope
import tech.gelab.cardiograph.scanner.api.ScannerApi
import javax.inject.Inject

class DeviceClickUseCase @Inject constructor() {

    fun invoke(scope: CoroutineScope, discoveredDevice: ScannerApi.DiscoveredDevice) {

    }

}