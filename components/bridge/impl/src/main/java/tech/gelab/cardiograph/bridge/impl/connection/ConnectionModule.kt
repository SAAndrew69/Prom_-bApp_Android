package tech.gelab.cardiograph.bridge.impl.connection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
class ConnectionModule {

    @Provides
    fun provideCardioBleManager(@ApplicationContext context: Context): CardioBleManager {
        return CardioBleManager(context)
    }

}