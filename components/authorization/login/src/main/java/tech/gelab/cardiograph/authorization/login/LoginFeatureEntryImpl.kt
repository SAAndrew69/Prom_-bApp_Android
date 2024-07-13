package tech.gelab.cardiograph.authorization.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.login.presentation.LoginScreen
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.pairing.api.PairingApi
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import tech.gelab.cardiograph.ui.dialog.dialognavprovider.TextDialogFeatureEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginFeatureEntryImpl @Inject constructor(
    private val pairingApi: PairingApi,
    private val pairingFeatureEntry: PairingFeatureEntry,
    private val bottomNavigationFeatureEntry: BottomNavigationFeatureEntry,
    private val textDialogFeatureEntry: TextDialogFeatureEntry,
    private val resourceProvider: ResourceProvider
) : LoginFeatureEntry, FeatureEventHandler<LoginFeatureEvent> {

    private var globalNavController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        animatedComposable(route = ROUTE.name) {
            this@LoginFeatureEntryImpl.globalNavController = LocalNavHostProvider.current
            LoginScreen()
        }
    }

    override fun obtainEvent(event: LoginFeatureEvent) {
        when (event) {
            is LoginFeatureEvent.LoginFailure -> globalNavController?.navigate(
                textDialogFeatureEntry.start(
                    title = resourceProvider.getString(R.string.title_login_error),
                    message = event.message
                )
            )

            is LoginFeatureEvent.LoginSuccess -> if (pairingApi.connectionPassed()) globalNavController?.navigate(
                bottomNavigationFeatureEntry.start()
            )
            else globalNavController?.navigate(
                pairingFeatureEntry.getSearchRoute(goBackAvailable = false, skipAvailable = true)
            )

            LoginFeatureEvent.Skip -> if (pairingApi.connectionPassed()) globalNavController?.navigate(
                bottomNavigationFeatureEntry.start()
            ) else globalNavController?.navigate(
                pairingFeatureEntry.getSearchRoute(
                    goBackAvailable = true,
                    skipAvailable = true
                )
            )

            LoginFeatureEvent.PopBackStack -> globalNavController?.popBackStack()
        }
    }
}