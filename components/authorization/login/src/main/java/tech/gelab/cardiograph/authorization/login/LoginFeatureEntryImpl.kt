package tech.gelab.cardiograph.authorization.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.AuthApi
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.login.presentation.LoginScreen
import tech.gelab.cardiograph.authorization.login.presentation.LoginViewModel
import tech.gelab.cardiograph.authorization.util.defaultEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultExitTransition
import tech.gelab.cardiograph.authorization.util.defaultPopEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultPopExitTransition
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import javax.inject.Inject

class LoginFeatureEntryImpl @Inject constructor(
    private val scannerFeatureEntry: ScannerFeatureEntry
) : LoginFeatureEntry, FeatureEventHandler<LoginFeatureEvent> {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = ROUTE.name,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            this@LoginFeatureEntryImpl.navController = navController
            LoginScreen(
                viewModel = hiltViewModel(
                    creationCallback = { factory: LoginViewModel.Factory ->
                        factory.create(this@LoginFeatureEntryImpl)
                    }
                ),
                onBackClick = navController::popBackStack
            )
        }
    }

    override fun obtainEvent(event: LoginFeatureEvent) {
        when (event) {
            is LoginFeatureEvent.LoginFailure -> TODO()
            is LoginFeatureEvent.LoginSuccess -> navController?.navigate(
                scannerFeatureEntry.getScannerRoute(
                    false
                )
            )

            LoginFeatureEvent.NavigateToNext -> navController?.navigate(
                scannerFeatureEntry.getScannerRoute(
                    true
                )
            )
        }
    }

}