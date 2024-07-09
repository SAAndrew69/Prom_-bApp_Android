package tech.gelab.cardiograph.authorization.signup

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.SignUpFeatureEntry
import tech.gelab.cardiograph.authorization.signup.presentation.SignUpScreen
import tech.gelab.cardiograph.authorization.signup.presentation.SignUpViewModel
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import tech.gelab.cardiograph.ui.dialog.dialognavprovider.TextDialogFeatureEntry
import javax.inject.Inject

class SignUpFeatureEntryImpl @Inject constructor(
    private val scannerFeatureEntry: ScannerFeatureEntry,
    private val textDialogFeatureEntry: TextDialogFeatureEntry,
    private val resourceProvider: ResourceProvider
) :
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
            is SignUpFeatureEvent.SignUpSuccess -> navController?.navigate(
                scannerFeatureEntry.getScannerRoute(
                    false
                )
            )

            is SignUpFeatureEvent.SignUpFailure -> navController?.navigate(
                textDialogFeatureEntry.start(
                    title = resourceProvider.getString(R.string.title_sign_up_error),
                    message = event.message
                )
            )

            SignUpFeatureEvent.Skip -> navController?.navigate(
                scannerFeatureEntry.getScannerRoute(
                    true
                )
            )

            SignUpFeatureEvent.PopBackStack -> navController?.popBackStack()
        }
    }
}