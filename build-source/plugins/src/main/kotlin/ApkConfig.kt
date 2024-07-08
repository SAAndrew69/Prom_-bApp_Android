object ApkConfig {
    object AppVersion {
        const val major = 0
        const val minor = 0
        const val revision = 2
    }

    const val APPLICATION_ID = "tech.gelab.cardiograph"

    const val COMPILE_SDK = 34
    const val TARGET_SDK = 34

    const val MIN_SDK = 26
    const val VERSION_CODE = AppVersion.major * 100000 + AppVersion.minor * 10000 + AppVersion.revision
    const val VERSION_NAME = "${AppVersion.major}.${AppVersion.minor}.${AppVersion.revision}"
}