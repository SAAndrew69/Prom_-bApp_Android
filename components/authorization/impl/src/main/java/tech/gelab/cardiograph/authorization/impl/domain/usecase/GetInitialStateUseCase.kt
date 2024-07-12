package tech.gelab.cardiograph.authorization.impl.domain.usecase

import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenState
import tech.gelab.cardiograph.network.NetworkManager
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(private val networkManager: NetworkManager) {
    fun invoke(): WelcomeScreenState {
        return if (networkManager.isNetworkEnabled()) WelcomeScreenState.NetworkConnectable
        else WelcomeScreenState.NoNetwork
    }
}