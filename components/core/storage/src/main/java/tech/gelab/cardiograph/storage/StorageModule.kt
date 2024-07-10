package tech.gelab.cardiograph.storage

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.storage.pb.AuthorizationSettings
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import tech.gelab.cardiograph.storage.pb.Settings
import tech.gelab.cardiograph.storage.serializer.AuthorizationSettingsSerializer.authorizationSettingsDataStore
import tech.gelab.cardiograph.storage.serializer.DeviceSettingsSerializer.deviceSettingsDataStore
import tech.gelab.cardiograph.storage.serializer.SettingsSerializer.settingsDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideAuthorizationSettingsDataStore(@ApplicationContext context: Context): DataStore<AuthorizationSettings> {
        return context.authorizationSettingsDataStore
    }

    @Provides
    @Singleton
    fun provideDeviceSettingsDataStore(@ApplicationContext context: Context): DataStore<DeviceSettings> {
        return context.deviceSettingsDataStore
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): DataStore<Settings> {
        return context.settingsDataStore
    }

}