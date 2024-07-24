package tech.gelab.cardiograph.measurement.api

import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

interface MeasurementFeatureEntry: ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.MEASUREMENT

    fun start(): String

}