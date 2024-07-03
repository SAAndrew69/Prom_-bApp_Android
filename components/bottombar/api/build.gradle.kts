plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.bottombar.api"
}

dependencies {
    implementation(projects.components.core.navigation)

    implementation(libs.appcompat)
}