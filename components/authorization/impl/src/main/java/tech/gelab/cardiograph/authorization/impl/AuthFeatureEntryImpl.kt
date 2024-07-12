package tech.gelab.cardiograph.authorization.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import tech.gelab.cardiograph.authorization.api.AuthFeatureEntry
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.api.SignUpFeatureEntry
import tech.gelab.cardiograph.authorization.api.SkipAuthFeatureEntry
import tech.gelab.cardiograph.authorization.impl.presentation.WelcomeScreen
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthFeatureEntryImpl @Inject constructor(
    private val loginFeatureEntry: LoginFeatureEntry,
    private val sighUpFeatureEntry: SignUpFeatureEntry,
    private val skipAuthFeatureEntry: SkipAuthFeatureEntry,
    private val pairingFeatureEntry: PairingFeatureEntry
) : AuthFeatureEntry, FeatureEventHandler<AuthFeatureEvent> {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        animatedComposable(route = NavigationRoute.WELCOME.name) {
            this@AuthFeatureEntryImpl.navController = navController
            WelcomeScreen()
        }
    }

    override fun obtainEvent(event: AuthFeatureEvent) {
        when (event) {
            AuthFeatureEvent.NavigateLogin -> navController?.navigate(loginFeatureEntry.ROUTE.name)
            AuthFeatureEvent.NavigateSignUp -> navController?.navigate(sighUpFeatureEntry.ROUTE.name)
            AuthFeatureEvent.NavigateSkipAuth -> navController?.navigate(skipAuthFeatureEntry.ROUTE.name)
            AuthFeatureEvent.OpenNetworkSettings -> {}
            AuthFeatureEvent.NavigateScanner -> navController?.navigate(pairingFeatureEntry.getSearchRoute(false))
        }
    }
}