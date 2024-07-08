package tech.gelab.cardiograph.authorization.login

sealed interface LoginFeatureEvent {
    data class LoginSuccess(val token: String) : LoginFeatureEvent
    data class LoginFailure(val message: String) : LoginFeatureEvent
    data object NavigateToNext : LoginFeatureEvent
}