package tech.gelab.cardiograph.scanner.impl

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navigation
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import tech.gelab.cardiograph.scanner.impl.domain.model.action.ScannerScreenAction
import tech.gelab.cardiograph.scanner.impl.presentation.ScannerScreen
import tech.gelab.cardiograph.ui.ktx.dialog.CardioAppDialog
import javax.inject.Inject

class ScannerFeatureEntryImpl @Inject constructor(
    private val bottomNavigationFeatureEntry: BottomNavigationFeatureEntry,
) : ScannerFeatureEntry {

    companion object {
        const val DEVICE_ADDRESS = "address"
        const val DEVICE_NAME = "name"
        const val GO_BACK_AVAILABLE = "go_back_available"
    }

    private val scannerArguments = listOf(
        navArgument(GO_BACK_AVAILABLE) {
            type = NavType.BoolType
            defaultValue = false
        }
    )

    private val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) by lazy {
        {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
        }
    }

    private val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) by lazy {
        {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start)
        }
    }

    private val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) by lazy {
        {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
        }
    }

    override fun start(): String {
        return "${NavigationRoute.SCANNER}?$GO_BACK_AVAILABLE={$GO_BACK_AVAILABLE}"
    }

    override fun getScannerRoute(goBackAvailable: Boolean): String {
        return "${NavigationRoute.SCANNER}?$GO_BACK_AVAILABLE=$goBackAvailable"
    }

    override fun getConnectionDialog(discoveredDevice: ScannerApi.DiscoveredDevice): String {
        return "${NavigationRoute.AUTH_ERROR_DIALOG.name}?$DEVICE_ADDRESS={${discoveredDevice.address}}&$DEVICE_NAME=${discoveredDevice.name}"
    }

    override fun NavGraphBuilder.navigation(navController: NavController) {
        navigation(
            route = ROUTE.name,
            startDestination = start(),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popExitTransition = popExitTransition
        ) {
            composable(
                route = start(),
                arguments = scannerArguments
            ) { backStackEntry ->
                val goBackAvailable = backStackEntry.arguments?.getBoolean(GO_BACK_AVAILABLE)
                ScannerScreen(
                    goBackAvailable = goBackAvailable!!,
                    onScannerScreenAction = { onScannerAction(navController, it) })
            }
            dialog(route = "${NavigationRoute.AUTH_ERROR_DIALOG.name}?$DEVICE_ADDRESS={$DEVICE_ADDRESS}&$DEVICE_NAME=${DEVICE_NAME}") {
                CardioAppDialog(
                    buttonLabelId = R.string.label_cancel,
                    onClickButton = { navController.popBackStack() })
            }
        }
    }

    private fun navigateToBottomNavigation(navController: NavController) {
        navController.navigate(bottomNavigationFeatureEntry.start()) {
            navController.graph.findStartDestination().route?.let {
                popUpTo(route = it) {
                    inclusive = true
                }
            }
        }
    }

    private fun onScannerAction(navController: NavController, scannerAction: ScannerScreenAction) {
        when (scannerAction) {
            ScannerScreenAction.ConnectionEstablished -> navigateToBottomNavigation(navController)
            ScannerScreenAction.GoBack -> navController.popBackStack()
            is ScannerScreenAction.RequestPermissions -> TODO()
            ScannerScreenAction.SkipDeviceSearch -> navigateToBottomNavigation(navController)
        }
    }
}