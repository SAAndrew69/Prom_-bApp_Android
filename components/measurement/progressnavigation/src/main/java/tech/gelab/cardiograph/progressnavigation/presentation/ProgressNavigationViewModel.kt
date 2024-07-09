package tech.gelab.cardiograph.progressnavigation.presentation

import androidx.navigation.NavDestination
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.measurement.progressnavigation.R
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavAction
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavEvent
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavState
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.cardiograph.ui.topbar.TopBarState
import timber.log.Timber

class ProgressNavigationViewModel :
    BaseViewModel<ProgressNavState, ProgressNavAction, ProgressNavEvent>(
        ProgressNavState()
    ) {
    override fun obtainEvent(viewEvent: ProgressNavEvent) {
        when (viewEvent) {
            is ProgressNavEvent.DestinationChanged -> onDestinationChanged(viewEvent)
        }
    }

    private fun getTopBarState(navDestination: NavDestination): TopBarState {
        val route = navDestination.route
        var titleId: Int? = null
        var backButtonEnabled: Boolean = true
        var progress: Float? = null
        if (route?.contains(NavigationRoute.ELECTRODE_CONNECTION.name) == true) {
            titleId = R.string.title_prepare_measure
            progress = 0.5f
        } else if (route?.contains(NavigationRoute.SIGNAL_QUALITY.name) == true) {
            titleId = R.string.title_prepare_measure
            progress = 1f
        } else if (route?.contains(NavigationRoute.MEASUREMENT.name) == true) {
            titleId = R.string.title_measure
            backButtonEnabled = false
        } else {
            titleId = R.string.title_prepare_measure
        }
        return TopBarState(titleId, showBackButton = backButtonEnabled, progress = progress)
    }

    private fun onDestinationChanged(event: ProgressNavEvent.DestinationChanged) {
        Timber.d("Progress navigation dest changed: ${event.navigationEvent.destination}")
        val topBarState = getTopBarState(event.navigationEvent.destination)
        viewState = viewState.copy(topBarState = topBarState)
    }
}