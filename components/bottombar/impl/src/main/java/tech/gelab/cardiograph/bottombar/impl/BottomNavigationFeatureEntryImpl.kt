package tech.gelab.cardiograph.bottombar.impl

import androidx.datastore.core.DataStore
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.bottombar.impl.bottombar.NavigationItem
import tech.gelab.cardiograph.bottombar.impl.presentation.BottomNavigationScreen
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.core.ui.navigation.defaultEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultExitTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopEnterTransition
import tech.gelab.cardiograph.core.ui.navigation.defaultPopExitTransition
import tech.gelab.cardiograph.storage.pb.Settings
import javax.inject.Inject
import javax.inject.Provider

class BottomNavigationFeatureEntryImpl @Inject constructor(
    composableFeatureEntries: Provider<MutableSet<ComposableFeatureEntry>>,
    aggregateFeatureEntries: Provider<MutableSet<AggregateFeatureEntry>>,
    private val settingsDataStore: DataStore<Settings>
) : BottomNavigationFeatureEntry {

    private val composableEntries = lazy {
        composableFeatureEntries.get().toPersistentSet()
    }
    private val aggregateEntries = lazy {
        aggregateFeatureEntries.get().toPersistentSet()
    }

    private var childNavController: NavHostController? = null

    private fun getStartDestination(): String {
        return runBlocking { NavigationItem.entries[settingsDataStore.data.first().selectedItemValue].route.name }
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