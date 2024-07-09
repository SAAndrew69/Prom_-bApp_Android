plugins {
    id("gelab.cardiograph.android-compose")
    kotlin("kapt")
}

android {
    namespace = "tech.gelab.cardiograph.measurement.progressnavigation"
}

dependencies {
    implementation(projects.components.measurement.api)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)
    implementation(projects.components.core.ui.topbar)

    implementation(libs.appcompat)
    implementation(libs.timber)
    implementation(libs.kotlin.immutable.collections)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
}