package tech.gelab.cardiograph.reports.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.reports.impl.R
import tech.gelab.cardiograph.reports.impl.domain.ReportsAction
import tech.gelab.cardiograph.reports.impl.domain.ReportsScreenState

@Composable
fun ReportsScreen(viewModel: ReportsViewModel = hiltViewModel()) {
    val state by viewModel.viewStates().collectAsState()
    val action by viewModel.viewActions().collectAsState(initial = null)
    ReportsView(Modifier.fillMaxSize(), state, action)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsView(modifier: Modifier = Modifier, state: ReportsScreenState, action: ReportsAction?) {
    val pullToRefreshState = rememberPullToRefreshState(enabled = { state.pullRefreshAvailable })
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.cachedReports.isEmpty() && state.availableReports.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(R.string.text_empty_reports_data),
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (state.cachedReports.isNotEmpty()) {
                item {
                    CachedReportsList(
                        modifier = Modifier.fillMaxWidth(),
                        reports = state.cachedReports
                    )
                }
            }
            if (state.availableReports.isNotEmpty()) {
                item {
                    AvailableReportsList(
                        modifier = Modifier.fillMaxWidth(),
                        reports = state.availableReports
                    )
                }
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState
        )
    }
}