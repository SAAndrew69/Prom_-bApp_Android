plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.measurement.api"
}

dependencies {
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)
}