package tech.gelab.cardiograph.conclusion.api

import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

interface ConclusionFeatureEntry : ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.CONCLUSION

}