package tech.gelab.cardiograph.measurement.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import javax.inject.Inject

class MeasurementFeatureEntryImpl @Inject constructor() : MeasurementFeatureEntry {

    override fun start(): String {
        return ROUTE.name
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {

        }
    }
}