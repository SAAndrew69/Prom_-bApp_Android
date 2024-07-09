package tech.gelab.cardiograph.bottombar.impl.presentation.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.collections.immutable.ImmutableSet
import tech.gelab.cardiograph.bottombar.impl.bottombar.CardioBottomBar
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationAction
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationEvent
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationState
import tech.gelab.cardiograph.bottombar.impl.presentation.viewmodel.BottomNavigationViewModel
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.DestinationChangedEffect
import tech.gelab.cardiograph.core.ui.navigation.rememberGraph
import tech.gelab.cardiograph.ui.topbar.CardioAppBar


@Composable
fun BottomNavigationScreen(
    viewModel: BottomNavigationViewModel = hiltViewModel(),
    composableEntries: ImmutableSet<ComposableFeatureEntry>,
    aggregateEntries: ImmutableSet<AggregateFeatureEntry>,
    startDestination: String,
    navController: NavHostController
) {
    DestinationChangedEffect(
        navController = navController,
        onDestinationChanged = {
            viewModel.obtainEvent(BottomNavigationEvent.GraphDestinationChanged(it))
        }
    )

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(initial = null)

    BottomNavigationView(
        viewState = viewState,
        viewAction = viewAction,
        startDestination = startDestination,
        composableEntries = composableEntries,
        aggregateEntries = aggregateEntries,
        navController = navController,
        onEvent = viewModel::obtainEvent
    )
}

@Composable
fun BottomNavigationView(
    modifier: Modifier = Modifier,
    viewState: BottomNavigationState,
    viewAction: BottomNavigationAction?,
    startDestination: String,
    composableEntries: ImmutableSet<ComposableFeatureEntry>,
    aggregateEntries: ImmutableSet<AggregateFeatureEntry>,
    navController: NavHostController,
    onEvent: (BottomNavigationEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CardioAppBar(topBarState = viewState.topBarState)
        },
        bottomBar = {
            CardioBottomBar(
                selectedIndex = viewState.selectedItemIndex,
                onNavigationItemClick = { onEvent(BottomNavigationEvent.NavigationItemClick(it)) }
            )
        }) { paddingValues ->
        val graph = rememberGraph(
            navController = navController,
            startDestination = startDestination,
            composableEntries = composableEntries,
            aggregateEntries = aggregateEntries
        )

        if (viewAction is BottomNavigationAction.ChangeDestination) {
            navController.navigate(viewAction.route) {
                launchSingleTop = true
            }
        }

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            graph = graph
        )
    }

}