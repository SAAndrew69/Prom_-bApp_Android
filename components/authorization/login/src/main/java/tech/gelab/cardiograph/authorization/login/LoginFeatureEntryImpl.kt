package tech.gelab.cardiograph.authorization.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.login.presentation.LoginScreen
import tech.gelab.cardiograph.authorization.login.presentation.LoginViewModel
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import tech.gelab.cardiograph.ui.dialog.dialognavprovider.TextDialogFeatureEntry
import javax.inject.Inject

class LoginFeatureEntryImpl @Inject constructor(
    private val pairingFeatureEntry: PairingFeatureEntry,
    private val textDialogFeatureEntry: TextDialogFeatureEntry,
    private val resourceProvider: ResourceProvider
) : LoginFeatureEntry, FeatureEventHandler<LoginFeatureEvent> {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        animatedComposable(route = ROUTE.name) {
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
            is LoginFeatureEvent.LoginFailure -> navController?.navigate(
                textDialogFeatureEntry.start(
                    title = resourceProvider.getString(R.string.title_login_error),
                    message = event.message
                )
            )

            is LoginFeatureEvent.LoginSuccess -> navController?.navigate(
                pairingFeatureEntry.getSearchRoute(
                    false
                )
            )

            LoginFeatureEvent.NavigateToNext -> navController?.navigate(
                pairingFeatureEntry.getSearchRoute(
                    true
                )
            )

            LoginFeatureEvent.PopBackStack -> navController?.popBackStack()
        }
    }

}