package tech.gelab.cardiograph.scanner.api

import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.scanner.api.internal.ScannerFeatureEvent

interface ScannerFeatureEntry : AggregateFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.SCANNER_ROOT

    fun start(): String
    fun getScannerRoute(goBackAvailable: Boolean): String
    fun getConnectionDialog(discoveredDevice: ScannerApi.DiscoveredDevice): String

}