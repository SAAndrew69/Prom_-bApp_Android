package tech.gelab.cardiograph.authorization.impl.domain

sealed interface WelcomeScreenState {
    data object NetworkConnectable : WelcomeScreenState
    data object NoNetwork : WelcomeScreenState
}