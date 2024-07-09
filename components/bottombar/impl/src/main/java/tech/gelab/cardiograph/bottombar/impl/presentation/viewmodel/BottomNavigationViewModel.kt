package tech.gelab.cardiograph.bottombar.impl.presentation.viewmodel

import androidx.navigation.NavDestination
import tech.gelab.cardiograph.bottombar.impl.R
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationAction
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationEvent
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationState
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.cardiograph.ui.topbar.TopBarState
import timber.log.Timber

class BottomNavigationViewModel :
    BaseViewModel<BottomNavigationState, BottomNavigationAction, BottomNavigationEvent>(
        BottomNavigationState()
    ) {

    override fun obtainEvent(viewEvent: BottomNavigationEvent) {
        when (viewEvent) {
            is BottomNavigationEvent.NavigationItemClick -> onNavigationItemClick(viewEvent)
            is BottomNavigationEvent.GraphDestinationChanged -> onNavDestinationChange(viewEvent)
        }
    }

    private fun getViewState(destination: NavDestination): BottomNavigationState {
        val titleId: Int
        var selectedIndex: Int? =null
        if (destination.route?.contains(NavigationRoute.IDENTIFIER_PICKER.name) == true) {
            titleId = R.string.title_measure_preparation
            selectedIndex = 0
        } else if (destination.route?.contains(NavigationRoute.REPORTS.name) == true) {
            titleId = R.string.title_reports
            selectedIndex = 1
        } else if (destination.route?.contains(NavigationRoute.PROFILE.name) == true) {
            titleId = R.string.title_profile
            selectedIndex = 2
        } else {
            titleId = R.string.title_default
        }

        return viewState.copy(
            topBarState = TopBarState(titleId),
            selectedItemIndex = selectedIndex ?: viewState.selectedItemIndex
        )
    }

    private fun onNavigationItemClick(viewEvent: BottomNavigationEvent.NavigationItemClick) {
        Timber.d("onNavigationItemClick: index = ${viewEvent.navigationItem}")
        viewAction = BottomNavigationAction.ChangeDestination(viewEvent.navigationItem.route.name)
    }

    private fun onNavDestinationChange(viewEvent: BottomNavigationEvent.GraphDestinationChanged) {
        viewState = getViewState(destination = viewEvent.navigationEvent.destination)
    }
}