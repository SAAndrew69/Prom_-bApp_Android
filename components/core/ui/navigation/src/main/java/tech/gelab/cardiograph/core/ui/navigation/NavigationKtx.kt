package tech.gelab.cardiograph.core.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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

fun NavGraphBuilder.animatedComposable(
    route: String,
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = defaultEnterTransition,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = defaultExitTransition,
    popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = defaultPopEnterTransition,
    popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = defaultPopExitTransition,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}

@Composable
fun rememberGraph(
    navController: NavController,
    startDestination: String,
    composableEntries: ImmutableSet<ComposableFeatureEntry>,
    aggregateEntries: ImmutableSet<AggregateFeatureEntry>
): NavGraph {
    return remember(startDestination, composableEntries, aggregateEntries) {
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
}

