package tech.gelab.cardiograph.bluetooth

class ScanFailedException(val error: Error): RuntimeException() {

    enum class Error(val code: Int) {
        /**
         * Fails to start scan as BLE scan with the same settings is already started by the app.
         */
        SCAN_FAILED_ALREADY_STARTED(1),

        /**
         * Fails to start scan as app cannot be registered.
         */
        SCAN_FAILED_APPLICATION_REGISTRATION_FAILED(2),

        /**
         * Fails to start scan due an internal error
         */
        SCAN_FAILED_INTERNAL_ERROR(3),

        /**
         * Fails to start power optimized scan as this feature is not supported.
         */
        SCAN_FAILED_FEATURE_UNSUPPORTED(4),

        /**
         * Fails to start scan as it is out of hardware resources.
         */
        SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES(5),

        /**
         * Fails to start scan as application tries to scan too frequently.
         */
        SCAN_FAILED_SCANNING_TOO_FREQUENTLY(6),

        NO_ERROR(0),

        UNKNOWN(-1);

        companion object {
            fun fromErrorCode(code: Int): Error {
                return entries.find { it.code == code} ?: UNKNOWN
            }
        }
    }

}