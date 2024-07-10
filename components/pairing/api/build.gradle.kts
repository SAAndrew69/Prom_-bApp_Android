plugins {
    id("cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.pairing.api"
}

dependencies {
    implementation(projects.components.core.storage)
    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.bridge.api)

    implementation(libs.appcompat)
}