package tech.gelab.cardiograph.scanner.api

import tech.gelab.cardiograph.core.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.navigation.NavigationRoute

interface ScannerFeatureEntry : AggregateFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.SCANNER_ROOT

    fun getScannerRoute(): String
    fun getConnectionDialog(discoveredDevice: ScannerApi.DiscoveredDevice): String

}