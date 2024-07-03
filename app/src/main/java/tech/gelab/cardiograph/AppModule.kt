package tech.gelab.cardiograph

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.util.ResourceProvider

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

}