package tech.gelab.cardiograph.bottombar.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.toPersistentSet
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.bottombar.impl.presentation.compose.BottomNavigationScreen
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import javax.inject.Inject
import javax.inject.Provider

class BottomNavigationFeatureEntryImpl @Inject constructor(
    composableFeatureEntries: Provider<MutableSet<ComposableFeatureEntry>>,
    aggregateFeatureEntries: Provider<MutableSet<AggregateFeatureEntry>>
) : BottomNavigationFeatureEntry {

    private val composableEntries = lazy {
        composableFeatureEntries.get().toPersistentSet()
    }
    private val aggregateEntries = lazy {
        aggregateFeatureEntries.get().toPersistentSet()
    }

    private var childNavController: NavHostController? = null

    // TODO
    private fun getStartDestination(): String {
        return NavigationRoute.IDENTIFIER_PICKER.name
    }

    override fun start(): String {
        return ROUTE.name
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(
            route = start(),
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultPopEnterTransition,
            popExitTransition = defaultPopExitTransition
        ) {
            val childNavController = rememberNavController().also {
                childNavController = it
            }
            BottomNavigationScreen(
                composableEntries = composableEntries.value,
                aggregateEntries = aggregateEntries.value,
                startDestination = getStartDestination(),
                navController = childNavController
            )
        }
    }
}