package tech.gelab.cardiograph.profile.impl

sealed interface ProfileFeatureEvent {
    data object NavigateAuthorization: ProfileFeatureEvent
}