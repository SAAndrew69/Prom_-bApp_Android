package tech.gelab.cardiograph.authorization.impl

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tech.gelab.cardiograph.authorization.api.AuthFeatureEntry
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.api.SignUpFeatureEntry
import tech.gelab.cardiograph.authorization.api.SkipAuthFeatureEntry
import tech.gelab.cardiograph.authorization.impl.presentation.compose.WelcomeScreen
import tech.gelab.cardiograph.authorization.impl.presentation.viewmodel.WelcomeScreenViewModel
import tech.gelab.cardiograph.authorization.util.defaultEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultExitTransition
import tech.gelab.cardiograph.authorization.util.defaultPopEnterTransition
import tech.gelab.cardiograph.authorization.util.defaultPopExitTransition
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import javax.inject.Inject

private const val dialogMessageArgName = "msg"

class AuthFeatureEntryImpl @Inject constructor(
    private val loginFeatureEntry: LoginFeatureEntry,
    private val sighUpFeatureEntry: SignUpFeatureEntry,
    private val skipAuthFeatureEntry: SkipAuthFeatureEntry,
    private val scannerFeatureEntry: ScannerFeatureEntry,
    private val toastHelper: ToastHelper,
) : AuthFeatureEntry, FeatureEventHandler<AuthFeatureEvent> {

    private val authFailureDialogArgs = listOf(
        navArgument(dialogMessageArgName) {
            type = NavType.IntType
            nullable = false
        }
    )
    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = NavigationRoute.WELCOME.name,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            this@AuthFeatureEntryImpl.navController = navController
            WelcomeScreen(viewModel = hiltViewModel(
                creationCallback = { factory: WelcomeScreenViewModel.Factory ->
                    factory.create(this@AuthFeatureEntryImpl)
                }
            ))
        }
    }


//    dialog(route = "${NavigationRoute.AUTH_ERROR_DIALOG.name}?$dialogMessageArgName={$dialogMessageArgName}") {
//        this@AuthFeatureEntryImpl.navController = LocalNavHostProvider.current
//        val message = it.arguments?.getString(dialogMessageArgName) ?: ""
//        CardioAppDialog(
//            title = stringResource(id = R.string.label_error),
//            text = message,
//            buttonLabel = stringResource(R.string.label_close),
//            onClickButton = { navController.popBackStack() },
//            closeOnClickOutside = true
//        )
//    }


    override fun obtainEvent(event: AuthFeatureEvent) {
        when (event) {
            AuthFeatureEvent.NavigateLogin -> navController?.navigate(loginFeatureEntry.ROUTE.name)
            AuthFeatureEvent.NavigateSignUp -> navController?.navigate(sighUpFeatureEntry.ROUTE.name)
            AuthFeatureEvent.NavigateSkipAuth -> navController?.navigate(skipAuthFeatureEntry.ROUTE.name)
        }
    }
}