package tech.gelab.cardiograph.pairing.api

import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.storage.pb.DeviceSettings

interface PairingFeatureEntry : AggregateFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.PAIRING

    fun start(): String
    fun getSearchRoute(goBackAvailable: Boolean, skipAvailable: Boolean): String
    fun getConnectionRoute(): String

}