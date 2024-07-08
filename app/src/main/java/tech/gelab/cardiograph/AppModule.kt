package tech.gelab.cardiograph

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bluetooth.permissions.BluetoothPermissions
import tech.gelab.cardiograph.bluetooth.permissions.PermissionChecker
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.network.NetworkManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Reusable
    fun provideToastHelper(@ApplicationContext context: Context): ToastHelper {
        return ToastHelper(context)
    }

    @Provides
    @Reusable
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @Reusable
    fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager {
        return NetworkManager(context)
    }

    @Provides
    @Singleton
    fun provideServicesStateProvider(
        @ApplicationContext context: Context,
        permissionChecker: PermissionChecker,
        @BluetoothPermissions permissions: Array<String>,
    ): ServicesStateProvider {
        return ServicesStateProvider(context, permissionChecker, permissions)
    }

}