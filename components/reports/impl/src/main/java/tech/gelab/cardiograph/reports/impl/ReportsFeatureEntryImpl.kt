package tech.gelab.cardiograph.reports.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.reports.api.ReportsFeatureEntry
import tech.gelab.cardiograph.reports.impl.presentation.ReportsScreen
import javax.inject.Inject

class ReportsFeatureEntryImpl @Inject constructor() : ReportsFeatureEntry {

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {
            ReportsScreen()
        }
    }

}