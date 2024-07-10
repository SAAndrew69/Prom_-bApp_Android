plugins {
    id("cardiograph.android-lib")
    alias(libs.plugins.android.library)
}

android {
    namespace = "tech.gelab.cardiograph.authorization.api"
}

dependencies {
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.navigation)
}