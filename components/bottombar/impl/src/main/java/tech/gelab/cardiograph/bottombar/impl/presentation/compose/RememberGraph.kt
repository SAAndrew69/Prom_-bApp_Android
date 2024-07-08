package tech.gelab.cardiograph.bottombar.impl.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.createGraph
import kotlinx.collections.immutable.ImmutableSet
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import timber.log.Timber

@Composable
internal fun rememberGraph(
    navController: NavController,
    startDestination: String,
    composableEntries: ImmutableSet<ComposableFeatureEntry>,
    aggregateEntries: ImmutableSet<AggregateFeatureEntry>
) : NavGraph {
    return remember(startDestination, composableEntries, aggregateEntries) {
        // TODO debug
        Timber.w("Graph creation")
        navController.createGraph(startDestination, null) {
            composableEntries.forEach {
                // TODO
                Timber.w(it.ROUTE.name)
                with(it) {
                    composable(navController)
                }
            }
            aggregateEntries.forEach {
                // TODO
                Timber.w(it.ROUTE.name)
                with(it) {
                    navigation(navController)
                }
            }
        }
    }
}