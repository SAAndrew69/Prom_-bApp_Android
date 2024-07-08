package tech.gelab.cardiograph.bottombar.impl.domain

sealed interface BottomNavigationAction {
    data class ChangeDestination(val route: String): BottomNavigationAction
}