package tech.gelab.cardiograph.authorization.signup.domain

data class SignUpScreenState(
    val signingUp: Boolean = false,
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val passwordConfirm: String = "",
    val passwordConfirmError: Boolean = false,
    val passwordVisible: Boolean = true
)
