package tech.gelab.cardiograph.authorization.impl

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.AuthFeatureEntry
import tech.gelab.cardiograph.authorization.impl.domain.AuthorizationEventHandler
import tech.gelab.cardiograph.authorization.impl.presentation.compose.AuthorizationScreen
import tech.gelab.cardiograph.authorization.impl.presentation.viewmodel.AuthorizationViewModel
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import javax.inject.Inject

class AuthFeatureEntryImpl @Inject constructor(
    private val scannerFeatureEntry: ScannerFeatureEntry,
) : AuthFeatureEntry, AuthorizationEventHandler {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {
            this@AuthFeatureEntryImpl.navController = navController
            AuthorizationScreen(hiltViewModel(
                creationCallback = { factory: AuthorizationViewModel.Factory ->
                    factory.create(this@AuthFeatureEntryImpl)
                }
            ))
        }
    }

    override fun onAuthorizationSuccess() {
        navController?.navigate(scannerFeatureEntry.ROUTE.name)
    }

    override fun onAuthorizationSkip() {
        navController?.navigate(scannerFeatureEntry.ROUTE.name)
    }
}