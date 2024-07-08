package tech.gelab.cardiograph.scanner.impl.domain.model.action

sealed interface ScannerScreenAction {
    data class RequestPermissions(val permissions: Array<String>) : ScannerScreenAction {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as RequestPermissions

            return permissions.contentEquals(other.permissions)
        }

        override fun hashCode(): Int {
            return permissions.contentHashCode()
        }
    }

    data object ConnectionEstablished : ScannerScreenAction
    data object SkipDeviceSearch : ScannerScreenAction
    data object GoBack : ScannerScreenAction
}