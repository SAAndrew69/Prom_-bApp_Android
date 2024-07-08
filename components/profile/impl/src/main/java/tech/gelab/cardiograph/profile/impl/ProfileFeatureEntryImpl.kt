package tech.gelab.cardiograph.profile.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.profile.api.ProfileFeatureEntry
import tech.gelab.cardiograph.profile.impl.presentation.ProfileScreen
import javax.inject.Inject

class ProfileFeatureEntryImpl @Inject constructor(): ProfileFeatureEntry {

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {
            ProfileScreen()
        }
    }

}