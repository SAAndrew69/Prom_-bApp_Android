package tech.gelab.cardiograph.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

class NetworkManager(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkEnabled(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return if (capabilities == null) false
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) true
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) true
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) true
        else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }

    fun getNetworkStateFlow(): Flow<Boolean> {
        return callbackFlow {

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                }

            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
    }

}