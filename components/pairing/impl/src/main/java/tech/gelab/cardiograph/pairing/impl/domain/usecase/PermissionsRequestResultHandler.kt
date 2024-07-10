package tech.gelab.cardiograph.pairing.impl.domain.usecase

import tech.gelab.cardiograph.pairing.impl.domain.SearchEvent
import timber.log.Timber
import javax.inject.Inject

class PermissionsRequestResultHandler @Inject constructor() {

    private var deniedCounter = 0

    fun checkShouldOpenSettings(searchEvent: SearchEvent.PermissionRequestResultReceive): Boolean {
        Timber.d("checkShouldOpenSettings: ${searchEvent.permissions}")
        val deniedPermissions = searchEvent.permissions.entries.filter { !it.value }.map { it.key }
        Timber.d("checkShouldOpenSettings: denied permissions = $deniedPermissions, denied counter = $deniedCounter")
        if (deniedPermissions.isNotEmpty()) {
            deniedCounter++
        }
        return deniedCounter >= 2
    }
}