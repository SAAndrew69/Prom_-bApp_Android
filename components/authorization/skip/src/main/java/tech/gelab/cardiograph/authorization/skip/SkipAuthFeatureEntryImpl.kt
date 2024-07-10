package tech.gelab.cardiograph.authorization.skip

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.SkipAuthFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject

class SkipAuthFeatureEntryImpl @Inject constructor(
    private val pairingFeatureEntry: PairingFeatureEntry
) :
    SkipAuthFeatureEntry {
    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = ROUTE.name,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            SkipAuthScreen {
                when (it) {
                    SkipAuthEvent.GET_BACK -> navController.popBackStack()
                    SkipAuthEvent.CONTINUE -> navController.navigate(pairingFeatureEntry.start()) {
                        popUpTo(route = NavigationRoute.WELCOME.name)
                    }
                }
            }
        }
    }
}