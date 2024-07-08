plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.scanner.api"
}

dependencies {
    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.bridge.api)

    implementation(libs.appcompat)
}