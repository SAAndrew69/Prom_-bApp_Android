package tech.gelab.cardiograph.idpicker.api

import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

interface IdentifierPickerFeatureEntry: ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.IDENTIFIER_PICKER

}