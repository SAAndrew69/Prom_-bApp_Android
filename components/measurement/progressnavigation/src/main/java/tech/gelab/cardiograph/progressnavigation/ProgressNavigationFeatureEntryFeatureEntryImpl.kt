package tech.gelab.cardiograph.progressnavigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.measurement.api.ProgressNavigationFeatureEntry
import javax.inject.Inject

class ProgressNavigationFeatureEntryFeatureEntryImpl @Inject constructor(): ProgressNavigationFeatureEntry {

    override fun start(): String {
        return ROUTE.name
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = start()) {

        }
    }
}