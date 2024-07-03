package tech.gelab.cardiograph.bluetooth.permissions

import javax.inject.Named
import javax.inject.Qualifier

@Qualifier
@Named("bluetooth_runtime")
annotation class BluetoothPermissions

@Qualifier
@Named("min_sdk")
annotation class MinSdk

@Qualifier
@Named("is_never_for_location")
annotation class IsNeverForLocation