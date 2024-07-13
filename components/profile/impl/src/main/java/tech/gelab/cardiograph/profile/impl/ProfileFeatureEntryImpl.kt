package tech.gelab.cardiograph.profile.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.authorization.api.AuthFeatureEntry
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.profile.api.ProfileFeatureEntry
import tech.gelab.cardiograph.profile.impl.presentation.ProfileScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileFeatureEntryImpl @Inject constructor(
    private val loginFeatureEntry: LoginFeatureEntry
) : ProfileFeatureEntry, FeatureEventHandler<ProfileFeatureEvent> {

    private var globalNavController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {
            globalNavController = LocalNavHostProvider.current
            ProfileScreen()
        }
    }

    override fun obtainEvent(event: ProfileFeatureEvent) {
        when (event) {
            ProfileFeatureEvent.NavigateAuthorization -> globalNavController?.navigate(
                loginFeatureEntry.ROUTE.name
            )
        }
    }

}