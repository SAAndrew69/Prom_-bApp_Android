package tech.gelab.cardiograph.profile.impl.domain.usecase

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.profile.impl.R
import tech.gelab.cardiograph.profile.impl.domain.ProfileState
import tech.gelab.cardiograph.storage.pb.AuthorizationSettings
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService,
    private val deviceSettingsDataStore: DataStore<DeviceSettings>,
    private val authorizationSettingsDataStore: DataStore<AuthorizationSettings>,
    private val resourceProvider: ResourceProvider
) {

    fun invoke(): ProfileState {
        val authenticationSettings = runBlocking { authorizationSettingsDataStore.data.first() }

        val authPassed = authenticationSettings.authenticationComplete
        val disconnectAvailable =
            runBlocking { deviceSettingsDataStore.data.first().deviceConnectionPassed }

        val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName

        return ProfileState(
            authorized = authPassed,
            disconnectVisible = disconnectAvailable,
            appVersion = version,
            email = if (authenticationSettings.email.isNotEmpty()) authenticationSettings.email else null,
            message = if (!authPassed) resourceProvider.getString(R.string.text_not_authorized) else null
        )
    }
}