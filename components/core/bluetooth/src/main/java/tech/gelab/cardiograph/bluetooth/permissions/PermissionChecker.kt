package tech.gelab.cardiograph.bluetooth.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionChecker(private val context: Context) {

    fun checkPermissions(permissions: Array<String>): PermissionsState {
        return PermissionsState(buildList {
            for (permission in permissions) {
                val isGranted = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
                if (!isGranted) {
                    add(permission)
                }
            }
        })
    }
}