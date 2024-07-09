package tech.gelab.cardiograph.progressnavigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.toPersistentSet
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import tech.gelab.cardiograph.measurement.api.ProgressNavigationFeatureEntry
import tech.gelab.cardiograph.progressnavigation.presentation.ProgressNavigation
import javax.inject.Inject
import javax.inject.Provider

class ProgressNavigationFeatureEntryFeatureEntryImpl @Inject constructor(
    private val composableFeatureEntries: Provider<MutableSet<ComposableFeatureEntry>>,
    private val aggregateFeatureEntries: Provider<MutableSet<AggregateFeatureEntry>>
) : ProgressNavigationFeatureEntry {

    private var globalNavController: NavController? = null
    private var childNavController: NavController? = null

    private val composableEntries = lazy {
        composableFeatureEntries.get().toPersistentSet()
    }
    private val aggregateEntries = lazy {
        aggregateFeatureEntries.get().toPersistentSet()
    }

    override fun start(): String {
        return ROUTE.name
    }

    // TODO
    private fun getStartDestination(): String {
        return NavigationRoute.ELECTRODE_CONNECTION.name
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = start(),
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            val navHostController = rememberNavController().also {
                childNavController = it
            }
            globalNavController = LocalNavHostProvider.current
            ProgressNavigation(
                composableEntries = composableEntries.value,
                aggregateEntries = aggregateEntries.value,
                startDestination = getStartDestination(),
                navController = navHostController
            )
        }
    }
}