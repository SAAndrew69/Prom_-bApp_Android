package tech.gelab.cardiograph.progressnavigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import tech.gelab.cardiograph.measurement.api.SignalQualityFeatureEntry
import tech.gelab.cardiograph.progressnavigation.presentation.SignalQualityScreen
import javax.inject.Inject

class SignalQualityFeatureEntryImpl @Inject constructor(
    private val measurementFeatureEntry: MeasurementFeatureEntry
) : SignalQualityFeatureEntry {

    private var globalNavController: NavController? = null
    private var childNavController: NavController? = null

    private fun onNextClick() {
        globalNavController?.navigate(measurementFeatureEntry.start()) {
            popUpTo(route = NavigationRoute.BOTTOM_NAVIGATION.name) {
                inclusive = true
            }
//            globalNavController?.graph?.findStartDestination()?.route?.let {
//                popUpTo(route = it) {
//                    inclusive = true
//                }
//            }
        }
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = ROUTE.name,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popExitTransition = defaultPopExitTransition,
            popEnterTransition = defaultEnterTransition
        ) {
            globalNavController = LocalNavHostProvider.current
            SignalQualityScreen(onNextClick = this@SignalQualityFeatureEntryImpl::onNextClick)
        }
    }
}