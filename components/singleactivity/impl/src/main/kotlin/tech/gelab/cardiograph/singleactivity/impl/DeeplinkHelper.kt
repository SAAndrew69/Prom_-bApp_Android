package tech.gelab.cardiograph.singleactivity.impl

import tech.gelab.cardiograph.authorization.api.AuthFeatureEntry
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.network.NetworkManager
import tech.gelab.cardiograph.pairing.api.PairingApi
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject

class DeeplinkHelper @Inject constructor(
    private val authService: AuthService,
    private val pairingApi: PairingApi,
    private val authFeatureEntry: AuthFeatureEntry,
    private val pairingFeatureEntry: PairingFeatureEntry,
    private val bottomNavigationFeatureEntry: BottomNavigationFeatureEntry,
    private val networkManager: NetworkManager
) {
    fun getStartDestination(): String {
        return if (authService.shouldOpenAuthScreen() || !networkManager.isNetworkEnabled()) {
            authFeatureEntry.ROUTE.name
        } else if (!pairingApi.connectionPassed()) {
            pairingFeatureEntry.start()
        } else {
            bottomNavigationFeatureEntry.start()
        }
    }
}