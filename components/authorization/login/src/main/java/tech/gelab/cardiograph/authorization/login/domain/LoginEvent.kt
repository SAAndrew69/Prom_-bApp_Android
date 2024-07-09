package tech.gelab.cardiograph.authorization.login.domain

sealed interface LoginEvent {
    data class EmailUpdate(val value: String): LoginEvent
    data object EmailSubmit: LoginEvent
    data class PasswordUpdate(val value: String): LoginEvent
    data object PasswordSubmit: LoginEvent
    data object VisibilityClick: LoginEvent
    data object LoginClick: LoginEvent
    data object SkipClick: LoginEvent
    data object ForgotPasswordClick: LoginEvent
    data object BackClick: LoginEvent
}