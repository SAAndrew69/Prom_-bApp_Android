package tech.gelab.cardiograph.progressnavigation.domain

import tech.gelab.cardiograph.core.ui.navigation.NavigationEvent

sealed interface ProgressNavEvent {
    data class DestinationChanged(val navigationEvent: NavigationEvent): ProgressNavEvent
    data object BackClick : ProgressNavEvent
}