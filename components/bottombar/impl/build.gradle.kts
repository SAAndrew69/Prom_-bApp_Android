plugins {
    id("gelab.cardiograph.android-compose")
    kotlin("kapt")
}

android {
    namespace = "tech.gelab.cardiograph.bottombar.impl"
}

dependencies {
    implementation(projects.components.bottombar.api)

    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.topbar)
    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.ktx)

    implementation(libs.timber)
    implementation(libs.appcompat)
    implementation(libs.kotlin.immutable.collections)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}