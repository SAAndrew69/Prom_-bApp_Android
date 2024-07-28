package tech.gelab.cardiograph.idpicker.api

import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

interface NewEmployeeFeatureEntry : AggregateFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.NEW_EMPLOYEE

    fun start(id: Int): String

}