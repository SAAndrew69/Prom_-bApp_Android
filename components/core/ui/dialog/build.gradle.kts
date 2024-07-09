plugins {
    id("gelab.cardiograph.android-compose")
    kotlin("kapt")
}

android {
    namespace = "tech.gelab.cardiograph.ui.dialog"
}

dependencies {
    implementation(projects.components.core.ui.ktx)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}