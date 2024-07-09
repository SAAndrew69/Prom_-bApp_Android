package tech.gelab.cardiograph.bottombar.impl.domain

import tech.gelab.cardiograph.bottombar.impl.bottombar.NavigationItem
import tech.gelab.cardiograph.core.ui.navigation.NavigationEvent

sealed interface BottomNavigationEvent {
    data class NavigationItemClick(val navigationItem: NavigationItem) : BottomNavigationEvent
    data class GraphDestinationChanged(val navigationEvent: NavigationEvent) : BottomNavigationEvent
}