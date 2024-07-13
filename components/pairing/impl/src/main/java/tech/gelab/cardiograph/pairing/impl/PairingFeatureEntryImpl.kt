package tech.gelab.cardiograph.pairing.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.pairing.api.PairingApi
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import tech.gelab.cardiograph.pairing.impl.presentation.ConnectionScreen
import tech.gelab.cardiograph.pairing.impl.presentation.SearchScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PairingFeatureEntryImpl @Inject constructor(
    private val pairingApi: PairingApi,
    private val bottomNavigationFeatureEntry: BottomNavigationFeatureEntry,
) : PairingFeatureEntry, FeatureEventHandler<PairingFeatureEvent> {

    companion object {
        const val GO_BACK_AVAILABLE = "go_back_available"
        const val SKIP_AVAILABLE = "skip_available"

        val scannerRoute =
            "${NavigationRoute.PAIRING}search?$GO_BACK_AVAILABLE={$GO_BACK_AVAILABLE}&$SKIP_AVAILABLE={$SKIP_AVAILABLE}"
        val connectionRoute = "${NavigationRoute.PAIRING}connectionScreen"
    }

    private val arguments = listOf(
        navArgument(GO_BACK_AVAILABLE) {
            type = NavType.BoolType
            defaultValue = true
        },
        navArgument(SKIP_AVAILABLE) {
            type = NavType.BoolType
            defaultValue = true
        }
    )

    private var navController: NavController? = null

    override fun start(): String {
        return if (!pairingApi.connectionPassed()) getSearchRoute(
            goBackAvailable = true,
            skipAvailable = true
        ) else getConnectionRoute()
    }

    override fun getSearchRoute(goBackAvailable: Boolean, skipAvailable: Boolean): String {
        return "${NavigationRoute.PAIRING}search?$GO_BACK_AVAILABLE=$goBackAvailable&$SKIP_AVAILABLE=$skipAvailable"
    }

    // TODO must be id instead of address in future
    override fun getConnectionRoute(): String {
        return "${NavigationRoute.PAIRING}connectionScreen"
    }

    override fun NavGraphBuilder.navigation(navController: NavController) {
        navigation(
            route = ROUTE.name,
            startDestination = scannerRoute
        ) {
            animatedComposable(route = scannerRoute, arguments = arguments) { backStackEntry ->
                this@PairingFeatureEntryImpl.navController = navController
                SearchScreen()
            }
            animatedComposable(route = connectionRoute) {
                this@PairingFeatureEntryImpl.navController = navController
                ConnectionScreen()
            }
        }
    }


    override fun obtainEvent(event: PairingFeatureEvent) {
        when (event) {
            is PairingFeatureEvent.NavigateConnection -> navController?.navigate(getConnectionRoute())
            is PairingFeatureEvent.PopBackStack -> navController?.popBackStack()
            is PairingFeatureEvent.NavigateMainScreen -> navigateToBottomNavigation()
//            is PairingFeatureEvent.ConnectionFailed -> navController?.popBackStack()
        }
    }

    private fun navigateToBottomNavigation() {
        navController?.navigate(bottomNavigationFeatureEntry.start()) {
            navController?.graph?.findStartDestination()?.route?.let {
                popUpTo(route = it) {
                    inclusive = true
                }
            }
        }
    }
}