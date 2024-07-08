package tech.gelab.cardiograph.authorization.signup

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.SignUpFeatureEntry
import tech.gelab.cardiograph.authorization.signup.presentation.SignUpScreen
import tech.gelab.cardiograph.authorization.signup.presentation.SignUpViewModel
import tech.gelab.cardiograph.authorization.util.defaultEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultExitTransition
import tech.gelab.cardiograph.authorization.util.defaultPopEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultPopExitTransition
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import javax.inject.Inject

class SignUpFeatureEntryImpl @Inject constructor(private val scannerFeatureEntry: ScannerFeatureEntry) :
    SignUpFeatureEntry, FeatureEventHandler<SignUpFeatureEvent> {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = ROUTE.name,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            this@SignUpFeatureEntryImpl.navController = navController
            SignUpScreen(
                viewModel = hiltViewModel { factory: SignUpViewModel.Factory ->
                    factory.create(this@SignUpFeatureEntryImpl)
                }
            )
        }
    }

    override fun obtainEvent(event: SignUpFeatureEvent) {
        when (event) {
            is SignUpFeatureEvent.SignUpSuccess -> navController?.navigate(scannerFeatureEntry.getScannerRoute(false))
            is SignUpFeatureEvent.SignUpFailure -> TODO()
            SignUpFeatureEvent.Skip -> navController?.navigate(scannerFeatureEntry.getScannerRoute(true))
        }
    }
}