package tech.gelab.cardiograph

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.sentry.Sentry
import timber.log.Timber

@HiltAndroidApp
class CardiographApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}