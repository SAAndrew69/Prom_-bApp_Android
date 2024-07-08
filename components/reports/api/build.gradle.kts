plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.reports.api"
}

dependencies {
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)
}