package tech.gelab.cardiograph.progressnavigation.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.collections.immutable.ImmutableSet
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.DestinationChangedEffect
import tech.gelab.cardiograph.core.ui.navigation.rememberGraph
import tech.gelab.cardiograph.measurement.progressnavigation.R
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavAction
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavEvent
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavState
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState

@Composable
fun ProgressNavigation(
    viewModel: ProgressNavigationViewModel = hiltViewModel(),
    composableEntries: ImmutableSet<ComposableFeatureEntry>,
    aggregateEntries: ImmutableSet<AggregateFeatureEntry>,
    startDestination: String,
    navController: NavHostController
) {
    DestinationChangedEffect(
        navController = navController,
        onDestinationChanged = {
            viewModel.obtainEvent(ProgressNavEvent.DestinationChanged(it))
        }
    )

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(initial = null)

    ProgressNavigationView(
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
fun ProgressNavigationView(
    modifier: Modifier = Modifier,
    viewState: ProgressNavState,
    viewAction: ProgressNavAction?,
    startDestination: String,
    composableEntries: ImmutableSet<ComposableFeatureEntry>,
    aggregateEntries: ImmutableSet<AggregateFeatureEntry>,
    navController: NavHostController,
    onEvent: (ProgressNavEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ProgressNavigationBar(
                topBarState = viewState.topBarState,
                onBackButtonClick = { onEvent(ProgressNavEvent.BackClick) })
        }) { paddingValues ->
        val graph = rememberGraph(
            navController,
            startDestination,
            composableEntries,
            aggregateEntries
        )

        if (viewAction is ProgressNavAction.Navigate) {
            navController.navigate(viewAction.route)
        }

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            graph = graph
        )
    }
}

val progressNavIndicatorLabel = "indicator_label"

@Composable
fun ProgressNavigationBar(topBarState: TopBarState, onBackButtonClick: () -> Unit) {
    Column {
        CardioAppBar(topBarState = topBarState, onBackButtonClick = onBackButtonClick)
        val prog = topBarState.progress
        if (prog != null) {
            val progress by animateFloatAsState(
                targetValue = prog,
                label = progressNavIndicatorLabel
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small),
                strokeCap = StrokeCap.Round,
                progress = { progress })
        }
    }
}

@Preview
@Composable
private fun ProgressNavigationBarPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ProgressNavigationBar(
                topBarState = TopBarState(
                    R.string.title_prepare_measure,
                    showBackButton = true,
                    0.4f
                )
            ) {}
        }
    }
}