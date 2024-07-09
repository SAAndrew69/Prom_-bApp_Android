package tech.gelab.cardiograph.measurement.api

import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

interface ProgressNavigationFeatureEntry : ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.PROGRESS_NAVIGATION

    fun start(): String

}