package tech.gelab.cardiograph.authorization.impl

sealed interface AuthFeatureEvent {
    data object NavigateLogin : AuthFeatureEvent
    data object NavigateSignUp : AuthFeatureEvent
    data object NavigateSkipAuth : AuthFeatureEvent
}