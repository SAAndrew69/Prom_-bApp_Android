package tech.gelab.cardiograph.authorization.signup

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import tech.gelab.cardiograph.authorization.api.SignUpFeatureEntry
import tech.gelab.cardiograph.authorization.signup.presentation.SignUpScreen
import tech.gelab.cardiograph.authorization.signup.presentation.SignUpViewModel
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.pairing.api.PairingApi
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import tech.gelab.cardiograph.ui.dialog.dialognavprovider.TextDialogFeatureEntry
import javax.inject.Inject

class SignUpFeatureEntryImpl @Inject constructor(
    private val pairingApi: PairingApi,
    private val pairingFeatureEntry: PairingFeatureEntry,
    private val bottomNavigationFeatureEntry: BottomNavigationFeatureEntry,
    private val textDialogFeatureEntry: TextDialogFeatureEntry,
    private val resourceProvider: ResourceProvider
) : SignUpFeatureEntry, FeatureEventHandler<SignUpFeatureEvent> {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        animatedComposable(route = ROUTE.name) {
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
            is SignUpFeatureEvent.SignUpSuccess -> if (pairingApi.connectionPassed()) navController?.navigate(
                bottomNavigationFeatureEntry.start()
            ) else navController?.navigate(
                pairingFeatureEntry.getSearchRoute(
                    goBackAvailable = false,
                    skipAvailable = true
                )
            )

            is SignUpFeatureEvent.SignUpFailure -> navController?.navigate(
                textDialogFeatureEntry.start(
                    title = resourceProvider.getString(R.string.title_sign_up_error),
                    message = event.message
                )
            )

            SignUpFeatureEvent.Skip -> if (pairingApi.connectionPassed()) navController?.navigate(
                bottomNavigationFeatureEntry.start()
            ) else navController?.navigate(
                pairingFeatureEntry.getSearchRoute(
                    goBackAvailable = true,
                    skipAvailable = true
                )
            )

            SignUpFeatureEvent.PopBackStack -> navController?.popBackStack()
        }
    }
}