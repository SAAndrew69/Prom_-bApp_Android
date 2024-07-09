plugins {
    id("gelab.cardiograph.android-compose")
    kotlin("kapt")
}

android {
    namespace = "tech.gelab.cardiograph.scanner.impl"
}

dependencies {
    implementation(projects.components.scanner.api)
    implementation(projects.components.bottombar.api)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.dialog)
    implementation(projects.components.core.ui.topbar)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)

    implementation(projects.components.core.bluetooth)
    implementation(projects.components.core.util)

    implementation(libs.appcompat)
    implementation(libs.timber)
    implementation(libs.kotlin.immutable.collections)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.ble.scan)
    implementation(libs.ble.ktx)

    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}