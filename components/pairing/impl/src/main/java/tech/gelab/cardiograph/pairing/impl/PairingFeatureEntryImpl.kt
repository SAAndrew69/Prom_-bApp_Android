package tech.gelab.cardiograph.pairing.impl

import androidx.hilt.navigation.compose.hiltViewModel
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
import tech.gelab.cardiograph.pairing.impl.presentation.ConnectionScreenViewModel
import tech.gelab.cardiograph.pairing.impl.presentation.SearchScreen
import tech.gelab.cardiograph.pairing.impl.presentation.SearchScreenViewModel
import javax.inject.Inject

class PairingFeatureEntryImpl @Inject constructor(
    private val pairingApi: PairingApi,
    private val bottomNavigationFeatureEntry: BottomNavigationFeatureEntry,
) : PairingFeatureEntry, FeatureEventHandler<PairingFeatureEvent> {

    companion object {
        const val DEVICE_ADDRESS = "address"
        const val DEVICE_NAME = "name"
        const val GO_BACK_AVAILABLE = "go_back_available"

        val scannerRoute = "${NavigationRoute.PAIRING}search?$GO_BACK_AVAILABLE={$GO_BACK_AVAILABLE}"
        val connectionRoute = "${NavigationRoute.PAIRING}connectionScreen"
    }

    private val arguments = listOf(
        navArgument(GO_BACK_AVAILABLE) {
            type = NavType.BoolType
            defaultValue = false
        }
    )

    private var navController: NavController? = null

    override fun start(): String {
        return if (!pairingApi.connectionPassed()) getSearchRoute(true) else getConnectionRoute()
    }

    override fun getSearchRoute(goBackAvailable: Boolean): String {
        return "${NavigationRoute.PAIRING}search?$GO_BACK_AVAILABLE=$goBackAvailable"
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
                val goBackAvailable = backStackEntry.arguments?.getBoolean(GO_BACK_AVAILABLE)
                SearchScreen(goBackAvailable = goBackAvailable!!, viewModel = hiltViewModel(
                    creationCallback = { factory: SearchScreenViewModel.Factory ->
                        factory.create(this@PairingFeatureEntryImpl)
                    }
                ))
            }
            animatedComposable(route = connectionRoute) {
                this@PairingFeatureEntryImpl.navController = navController
                ConnectionScreen(viewModel = hiltViewModel(
                    creationCallback = { factory: ConnectionScreenViewModel.Factory ->
                        factory.create(this@PairingFeatureEntryImpl)
                    }
                ))
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