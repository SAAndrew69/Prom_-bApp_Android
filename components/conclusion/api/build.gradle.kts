plugins {
    id("cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.conclusion.api"
}

dependencies {
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)
}