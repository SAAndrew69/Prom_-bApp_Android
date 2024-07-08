package tech.gelab.cardiograph.profile.impl.domain

sealed interface ProfileState {

    data class LoggedOut(val message: String = ""): ProfileState

    data class Authorized(val email: String): ProfileState

}