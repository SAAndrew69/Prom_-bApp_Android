package tech.gelab.cardiograph.progressnavigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import tech.gelab.cardiograph.measurement.api.SignalQualityFeatureEntry
import tech.gelab.cardiograph.progressnavigation.presentation.ElectrodeConnectionScreen
import javax.inject.Inject

class ElectrodeConnectionFeatureEntryImpl @Inject constructor(
    private val signalQualityFeatureEntry: SignalQualityFeatureEntry
) : ElectrodeConnectionFeatureEntry {

    private var childNavController: NavController? = null

    private fun onNextClick() {
        childNavController?.navigate(signalQualityFeatureEntry.ROUTE.name) {
            popUpTo(ROUTE.name)
        }
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = ROUTE.name,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            childNavController = navController
            ElectrodeConnectionScreen(onNextClick = this@ElectrodeConnectionFeatureEntryImpl::onNextClick)
        }
    }
}