plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.profile.api"
}

dependencies {
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)
}