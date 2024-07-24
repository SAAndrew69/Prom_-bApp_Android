package tech.gelab.cardiograph.singleactivity.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.createGraph
import kotlinx.collections.immutable.ImmutableSet
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    composableEntries: ImmutableSet<ComposableFeatureEntry>,
    aggregateEntries: ImmutableSet<AggregateFeatureEntry>
) {
    val graph = remember(startDestination, composableEntries) {
        navController.createGraph(startDestination, null) {
            composableEntries.forEach {
                with(it) {
                    composable(navController)
                }
            }
            aggregateEntries.forEach {
                with(it) {
                    navigation(navController)
                }
            }
        }
    }

    NavHost(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        graph = graph
    )
}