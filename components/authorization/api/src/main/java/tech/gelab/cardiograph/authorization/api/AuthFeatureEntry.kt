package tech.gelab.cardiograph.authorization.api

import tech.gelab.cardiograph.core.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.navigation.NavigationRoute

interface AuthFeatureEntry : ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.AUTHORIZATION

}