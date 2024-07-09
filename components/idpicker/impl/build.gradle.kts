plugins {
    id("gelab.cardiograph.android-compose")
    kotlin("kapt")
}

android {
    namespace = "tech.gelab.cardiograph.idpicker.impl"
}

dependencies {
    implementation(projects.components.idpicker.api)
    implementation(projects.components.bridge.api)
    implementation(projects.components.scanner.api)
    implementation(projects.components.measurement.api)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)

    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)

    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}