package tech.gelab.cardiograph.progressnavigation.presentation

import androidx.navigation.NavDestination
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.measurement.progressnavigation.R
import tech.gelab.cardiograph.progressnavigation.ProgressNavFeatureEvent
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavAction
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavEvent
import tech.gelab.cardiograph.progressnavigation.domain.ProgressNavState
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.cardiograph.ui.topbar.TopBarState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel(assistedFactory = ProgressNavigationViewModel.Factory::class)
class ProgressNavigationViewModel @AssistedInject constructor(
    @Assisted private val progressNavigationFeatureEventHandler: FeatureEventHandler<ProgressNavFeatureEvent>
) : BaseViewModel<ProgressNavState, ProgressNavAction, ProgressNavEvent>(ProgressNavState()) {
    override fun obtainEvent(viewEvent: ProgressNavEvent) {
        when (viewEvent) {
            is ProgressNavEvent.DestinationChanged -> onDestinationChanged(viewEvent)
            ProgressNavEvent.BackClick -> progressNavigationFeatureEventHandler.obtainEvent(ProgressNavFeatureEvent.PopBackStack)
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
        Timber.d("Progress navigation dest changed: ${event.navigationEvent.destination}, prev entry = ${event.navigationEvent.navController.previousBackStackEntry?.destination}")
        val topBarState = getTopBarState(event.navigationEvent.destination)
        viewState = viewState.copy(topBarState = topBarState)
    }

    @AssistedFactory
    interface Factory {
        fun create(progressNavigationFeatureEventHandler: FeatureEventHandler<ProgressNavFeatureEvent>): ProgressNavigationViewModel
    }
}