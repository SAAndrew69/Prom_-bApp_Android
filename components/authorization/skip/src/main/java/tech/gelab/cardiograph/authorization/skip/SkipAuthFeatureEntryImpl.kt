package tech.gelab.cardiograph.authorization.skip

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.AuthFeatureEntry
import tech.gelab.cardiograph.authorization.api.SkipAuthFeatureEntry
import tech.gelab.cardiograph.authorization.util.defaultEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultExitTransition
import tech.gelab.cardiograph.authorization.util.defaultPopEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultPopExitTransition
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import javax.inject.Inject

class SkipAuthFeatureEntryImpl @Inject constructor(
    private val scannerFeatureEntry: ScannerFeatureEntry
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
                    SkipAuthEvent.CONTINUE -> navController.navigate(
                        scannerFeatureEntry.getScannerRoute(
                            true
                        )
                    ) {
                        popUpTo(route = NavigationRoute.WELCOME.name)
                    }
                }
            }
        }
    }
}