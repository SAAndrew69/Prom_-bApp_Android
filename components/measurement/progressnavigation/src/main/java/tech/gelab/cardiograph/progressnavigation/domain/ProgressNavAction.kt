package tech.gelab.cardiograph.progressnavigation.domain

sealed interface ProgressNavAction {
    data class Navigate(val route: String) : ProgressNavAction
}