package tech.gelab.cardiograph.bottombar.impl.domain

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import tech.gelab.cardiograph.bottombar.impl.bottombar.NavigationItem

sealed interface BottomNavigationEvent {

    data class NavigationItemClick(val navigationItem: NavigationItem) : BottomNavigationEvent

    data class GraphDestinationChanged(
        val navController: NavController,
        val navDestination: NavDestination,
        val arguments: Bundle?,
    ) : BottomNavigationEvent

}