package tech.gelab.cardiograph.authorization.impl.domain

interface AuthorizationEventHandler {

    fun onAuthorizationSuccess()

    fun onAuthorizationSkip()

}