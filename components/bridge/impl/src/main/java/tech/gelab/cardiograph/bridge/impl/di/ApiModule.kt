package tech.gelab.cardiograph.bridge.impl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bluetooth.permissions.BluetoothPermissions
import tech.gelab.cardiograph.bluetooth.permissions.PermissionChecker
import tech.gelab.cardiograph.bridge.api.CardioBleScanner
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.bridge.impl.CardiographApiImpl
import tech.gelab.cardiograph.bridge.impl.connection.CardioBleManager
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideCardiographApi(
        @ApplicationContext context: Context,
        servicesStateProvider: ServicesStateProvider,
        cardioBleScannerProvider: Provider<CardioBleScanner>,
        cardioBleManager: CardioBleManager
    ): CardiographApi {
        return CardiographApiImpl(
            context,
            servicesStateProvider,
            cardioBleScannerProvider,
            cardioBleManager
        )
    }

    @Provides
    @Singleton
    fun provideServicesStateProvider(
        @ApplicationContext context: Context,
        permissionsHelper: PermissionChecker,
        @BluetoothPermissions permissions: Array<String>
    ): ServicesStateProvider {
        return ServicesStateProvider(context, permissionsHelper, permissions)
    }

    @Provides
    @Singleton
    fun provideCardioBleManager(
        @ApplicationContext context: Context
    ): CardioBleManager {
        return CardioBleManager(context)
    }

}