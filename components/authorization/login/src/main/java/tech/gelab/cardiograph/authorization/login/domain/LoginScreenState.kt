package tech.gelab.cardiograph.authorization.login.domain

import androidx.compose.runtime.Immutable

@Immutable
data class LoginScreenState(
    val signingIn: Boolean = false,
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordVisible: Boolean = false
)