package tech.gelab.cardiograph.bridge.impl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.bluetooth.ServicesStateProvider
import tech.gelab.cardiograph.bridge.api.CardiographApi
import tech.gelab.cardiograph.bridge.impl.CardiographApiImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideCardiographApi(
        @ApplicationContext context: Context,
        servicesStateProvider: ServicesStateProvider,
    ): CardiographApi {
        return CardiographApiImpl(context, servicesStateProvider)
    }

}