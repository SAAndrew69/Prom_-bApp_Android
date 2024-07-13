package tech.gelab.cardiograph.authorization.skip

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import tech.gelab.cardiograph.authorization.api.SkipAuthFeatureEntry
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.pairing.api.PairingApi
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject

class SkipAuthFeatureEntryImpl @Inject constructor(
    private val pairingApi: PairingApi,
    private val pairingFeatureEntry: PairingFeatureEntry,
    private val bottomNavigationFeatureEntry: BottomNavigationFeatureEntry
) : SkipAuthFeatureEntry {
    override fun NavGraphBuilder.composable(navController: NavController) {
        animatedComposable(route = ROUTE.name) {
            SkipAuthScreen {
                when (it) {
                    SkipAuthEvent.GET_BACK -> navController.popBackStack()
                    SkipAuthEvent.CONTINUE -> if (pairingApi.connectionPassed()) navController.navigate(
                        bottomNavigationFeatureEntry.start()
                    ) else navController.navigate(
                        pairingFeatureEntry.getSearchRoute(goBackAvailable = true, skipAvailable = true)
                    )
                }
            }
        }
    }
}