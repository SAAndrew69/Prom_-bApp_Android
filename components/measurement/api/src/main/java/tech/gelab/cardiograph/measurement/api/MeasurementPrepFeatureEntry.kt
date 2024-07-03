package tech.gelab.cardiograph.measurement.api

import tech.gelab.cardiograph.core.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.navigation.NavigationRoute

interface MeasurementPrepFeatureEntry: ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.MEASURE_PREPARATION

}