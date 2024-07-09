package tech.gelab.cardiograph.measurement.api

import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

interface ElectrodeConnectionFeatureEntry: ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.ELECTRODE_CONNECTION

}