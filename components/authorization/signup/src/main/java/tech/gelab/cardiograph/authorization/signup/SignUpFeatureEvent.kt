package tech.gelab.cardiograph.authorization.signup

sealed interface SignUpFeatureEvent {
    data class SignUpSuccess(val token: String) : SignUpFeatureEvent
    data class SignUpFailure(val message: String) : SignUpFeatureEvent
    data object Skip : SignUpFeatureEvent
    data object PopBackStack : SignUpFeatureEvent
}