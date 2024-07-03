plugins {
    id("gelab.cardiograph.android-compose")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "tech.gelab.cardiograph.singleactivity.impl"
}

dependencies {
    implementation(projects.components.core.ui.theme)

    implementation(projects.components.core.navigation)

    implementation(libs.appcompat)
    implementation(libs.kotlin.immutable.collections)
    implementation(libs.timber)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}