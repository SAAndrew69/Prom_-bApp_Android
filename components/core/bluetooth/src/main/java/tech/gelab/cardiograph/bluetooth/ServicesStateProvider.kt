package tech.gelab.cardiograph.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import tech.gelab.cardiograph.bluetooth.permissions.BluetoothPermissions
import tech.gelab.cardiograph.bluetooth.permissions.PermissionChecker
import tech.gelab.cardiograph.bluetooth.permissions.PermissionsState
import timber.log.Timber

class ServicesStateProvider(
    private val context: Context,
    private val permissionChecker: PermissionChecker,
    @BluetoothPermissions private val bluetoothPermissions: Array<String>,
) {

    private val bluetoothManager = context.getSystemService(
        Context.BLUETOOTH_SERVICE
    ) as BluetoothManager

    private val bluetoothAdapter = bluetoothManager.adapter

    private val locationManager = context.getSystemService(
        Context.LOCATION_SERVICE
    ) as LocationManager

    // TODO remove this
    private val providerScope = CoroutineScope(Dispatchers.IO)

    private fun bluetoothFlow(): Flow<BluetoothState> {
        return callbackFlow {
            val bluetoothStateReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                        val state = intent.getIntExtra(
                            BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.STATE_OFF
                        )
                        trySendBlocking(BluetoothState.fromAdapterInt(state))
                    }
                }
            }
            context.registerReceiver(
                bluetoothStateReceiver,
                IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            )
            awaitClose {
                context.unregisterReceiver(bluetoothStateReceiver)
            }
        }
    }

    private fun locationFlow(): Flow<LocationState> {
        return callbackFlow {
            val locationStateReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                        trySendBlocking(getLocationState())
                    }
                }
            }
            context.registerReceiver(
                locationStateReceiver,
                IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
            )
            awaitClose {
                context.unregisterReceiver(locationStateReceiver)
            }
        }
    }

    private fun permissionsFlow(): Flow<PermissionsState> {
        return flow {
            while (true) {
                emit(permissionChecker.checkPermissions(bluetoothPermissions))
                delay(1000)
            }
        }
    }

    fun getLocationState(): LocationState {
        return if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        )
            LocationState.LOCATION_ENABLED
        else LocationState.LOCATION_DISABLED
    }

    fun getBluetoothState(): BluetoothState {
        return BluetoothState.fromAdapterInt(bluetoothAdapter.state)
    }

    fun getServicesState(): ServicesState {
        return ServicesState(
            bluetoothState = BluetoothState.fromAdapterInt(bluetoothAdapter.state),
            locationState = if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            ) LocationState.LOCATION_ENABLED else LocationState.LOCATION_DISABLED,
            permissionsState = permissionChecker.checkPermissions(bluetoothPermissions)
        )
    }

    fun getPermissionsState(): PermissionsState {
        return permissionChecker.checkPermissions(bluetoothPermissions)
    }

    // TODO make state flow
    fun getServicesStateFlow(): Flow<ServicesState> {
        return combine(
            bluetoothFlow().onStart { emit(getBluetoothState()) },
            locationFlow().onStart { emit(getLocationState()) },
            permissionsFlow().onStart { emit(getPermissionsState()) },
            ::ServicesState
        ).distinctUntilChanged()
    }
}