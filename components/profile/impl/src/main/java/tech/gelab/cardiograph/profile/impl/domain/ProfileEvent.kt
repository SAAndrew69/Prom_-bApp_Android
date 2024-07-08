package tech.gelab.cardiograph.profile.impl.domain

sealed interface ProfileEvent {
    data object Login : ProfileEvent
    data object Disconnect : ProfileEvent
    data object ChangeUser : ProfileEvent
    data object DeleteAccount : ProfileEvent
}