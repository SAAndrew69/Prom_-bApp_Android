package tech.gelab.cardiograph.authorization.skip

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import tech.gelab.cardiograph.authorization.api.SkipAuthFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject

class SkipAuthFeatureEntryImpl @Inject constructor(
    private val pairingFeatureEntry: PairingFeatureEntry
) : SkipAuthFeatureEntry {
    override fun NavGraphBuilder.composable(navController: NavController) {
        animatedComposable(route = ROUTE.name) {
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