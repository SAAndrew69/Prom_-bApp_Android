package tech.gelab.cardiograph.bluetooth.permissions

data class PermissionsState(
    val deniedPermissions: List<String>
) {
    fun isAllGranted(): Boolean {
        return deniedPermissions.isEmpty()
    }
}