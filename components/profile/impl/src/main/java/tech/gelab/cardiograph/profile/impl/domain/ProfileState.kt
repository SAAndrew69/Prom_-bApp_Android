package tech.gelab.cardiograph.profile.impl.domain

data class ProfileState(
    val authorized: Boolean,
    val disconnectVisible: Boolean,
    val appVersion: String,
    val email: String? = null,
    val message: String? = null
)