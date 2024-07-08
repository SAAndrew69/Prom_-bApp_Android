plugins {
    id("gelab.cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.ui.theme"
}

dependencies {
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.google.fonts)
    implementation(libs.compose.material3)
    implementation(libs.compose.accompanist.systemuicontroller)
}