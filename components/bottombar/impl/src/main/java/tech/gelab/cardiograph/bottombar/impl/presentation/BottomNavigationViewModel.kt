package tech.gelab.cardiograph.bottombar.impl.presentation

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.bottombar.impl.R
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationAction
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationEvent
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationState
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.storage.pb.SelectedBottomItem
import tech.gelab.cardiograph.storage.pb.Settings
import tech.gelab.cardiograph.storage.pb.copy
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import tech.gelab.cardiograph.ui.topbar.TopBarState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel @Inject constructor(
    private val settingsDatastore: DataStore<Settings>
) : BaseViewModel<BottomNavigationState, BottomNavigationAction, BottomNavigationEvent>(BottomNavigationState()) {

    override fun obtainEvent(viewEvent: BottomNavigationEvent) {
        when (viewEvent) {
            is BottomNavigationEvent.NavigationItemClick -> onNavigationItemClick(viewEvent)
            is BottomNavigationEvent.GraphDestinationChanged -> onNavDestinationChange(viewEvent)
        }
    }

    private fun onNavigationItemClick(viewEvent: BottomNavigationEvent.NavigationItemClick) {
        Timber.d("onNavigationItemClick: index = ${viewEvent.navigationItem}")
        viewAction = BottomNavigationAction.ChangeDestination(viewEvent.navigationItem.route.name)
    }

    private fun onNavDestinationChange(viewEvent: BottomNavigationEvent.GraphDestinationChanged) {
        val destination = viewEvent.navigationEvent.destination
        val titleId: Int
        var selectedIndex: Int? = null
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

        if (selectedIndex != null) {
            viewModelScope.launch {
                settingsDatastore.updateData { data ->
                    data.copy { selectedItem = SelectedBottomItem.entries[selectedIndex] }
                }
            }
        }

        viewState = viewState.copy(
            topBarState = TopBarState(titleId),
            selectedItemIndex = selectedIndex ?: viewState.selectedItemIndex
        )
    }
}