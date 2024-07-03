plugins {
    id("gelab.cardiograph.android-lib")
    alias(libs.plugins.android.library)
}

android {
    namespace = "tech.gelab.cardiograph.authorization.api"
}

dependencies {
    implementation(projects.components.core.navigation)

    implementation(libs.appcompat)
}