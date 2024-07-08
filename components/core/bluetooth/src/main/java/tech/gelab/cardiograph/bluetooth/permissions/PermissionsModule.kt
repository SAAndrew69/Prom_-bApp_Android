package tech.gelab.cardiograph.bluetooth.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
class PermissionsModule {

    @[Provides Reusable MinSdk]
    fun provideMinSdk(@ApplicationContext context: Context): Int {
        val deviceSdk = Build.VERSION.SDK_INT
        val targetSdk = try {
            context.packageManager
                .getApplicationInfo(context.packageName, 0)
                .targetSdkVersion
        } catch (catchThemAll: Throwable) {
            Int.MAX_VALUE
        }
        return minOf(deviceSdk, targetSdk)
    }

    @[Provides Reusable IsNeverForLocation]
    fun provideIsNeverForLocation(@ApplicationContext context: Context): Boolean {
        var result = false
        try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_PERMISSIONS
            )
            for (i in 0 until packageInfo.requestedPermissions.size) {
                if (Manifest.permission.BLUETOOTH_SCAN != packageInfo.requestedPermissions[i]) {
                    continue
                } else if (packageInfo.requestedPermissionsFlags[i]
                    and PackageInfo.REQUESTED_PERMISSION_NEVER_FOR_LOCATION != 0
                ) {
                    result = true
                    break
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e("Could not find application PackageInfo \n${e.message}")
        }
        return result
    }


    @SuppressLint("InlinedApi")
    @[Provides Reusable BluetoothPermissions]
    fun provideRuntimePermissions(
        @MinSdk minSdk: Int,
        @IsNeverForLocation isNeverForLocation: Boolean,
    ): Array<String> {
        return if (minSdk < Build.VERSION_CODES.M) {
            arrayOf()
        } else if (minSdk < Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else if (minSdk < Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            if (isNeverForLocation) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            } else {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }
}