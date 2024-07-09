package tech.gelab.cardiograph.authorization.login

import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.login.presentation.LoginScreen
import tech.gelab.cardiograph.authorization.login.presentation.LoginViewModel
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import tech.gelab.cardiograph.ui.dialog.dialognavprovider.TextDialogFeatureEntry
import javax.inject.Inject

class LoginFeatureEntryImpl @Inject constructor(
    private val scannerFeatureEntry: ScannerFeatureEntry,
    private val textDialogFeatureEntry: TextDialogFeatureEntry,
    private val resourceProvider: ResourceProvider
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
                )
            )
        }
    }

    override fun obtainEvent(event: LoginFeatureEvent) {
        when (event) {
            // TODO implement
            is LoginFeatureEvent.LoginFailure -> navController?.navigate(
                textDialogFeatureEntry.start(
                    title = resourceProvider.getString(R.string.title_login_error),
                    message = event.message
                )
            )

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

            LoginFeatureEvent.PopBackStack -> navController?.popBackStack()
        }
    }

}