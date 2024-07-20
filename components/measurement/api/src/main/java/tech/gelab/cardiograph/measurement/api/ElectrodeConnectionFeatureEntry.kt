package tech.gelab.cardiograph.measurement.api

import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

interface ElectrodeConnectionFeatureEntry: ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.ELECTRODE_CONNECTION

    /** Get route for dialog entry destination with given parameters
     *  @param nextDestinationRoute route, nav controller must navigate after user closes the dialog, will call NavController.popBackStack() if null
     *  @param showCheckBox show check box with show no more option*/
    fun start(nextDestinationRoute: String?, showCheckBox: Boolean): String

}