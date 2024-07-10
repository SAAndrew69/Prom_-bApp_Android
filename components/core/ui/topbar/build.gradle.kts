plugins {
    id("cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.ui.topbar"
}

dependencies {
    implementation(projects.components.core.ui.theme)

    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
}