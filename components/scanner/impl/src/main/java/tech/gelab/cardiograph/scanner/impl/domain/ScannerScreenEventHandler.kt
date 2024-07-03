package tech.gelab.cardiograph.scanner.impl.domain

import tech.gelab.cardiograph.scanner.api.ScannerApi

// TODO replace this boilerplate with generic event handler and base viewmodel
interface ScannerScreenEventHandler {

    fun onNextClick(discoveredDevice: ScannerApi.DiscoveredDevice)

    fun onSkipClick()

}