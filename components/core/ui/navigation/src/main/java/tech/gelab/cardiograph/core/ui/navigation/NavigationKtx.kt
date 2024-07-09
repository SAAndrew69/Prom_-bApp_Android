package tech.gelab.cardiograph.core.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.createGraph
import kotlinx.collections.immutable.ImmutableSet
import timber.log.Timber

val defaultEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) by lazy {
    {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
    }
}

val defaultExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) by lazy {
    {
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start)
    }
}

val defaultPopEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) by lazy {
    {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
    }
}

val defaultPopExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) by lazy {
    {
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
    }
}

@Composable
fun rememberGraph(
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

