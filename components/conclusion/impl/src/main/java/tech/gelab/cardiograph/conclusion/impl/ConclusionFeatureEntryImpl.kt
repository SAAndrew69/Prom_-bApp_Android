package tech.gelab.cardiograph.conclusion.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.conclusion.api.ConclusionFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConclusionFeatureEntryImpl @Inject constructor() : ConclusionFeatureEntry, FeatureEventHandler<ConclusionFeatureEvent> {

    private var globalNavController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {
            globalNavController = LocalNavHostProvider.current
        }
    }

    override fun obtainEvent(event: ConclusionFeatureEvent) {

    }
}