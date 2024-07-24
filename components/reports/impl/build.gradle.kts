plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.reports.impl"
}

dependencies {
    implementation(projects.components.reports.api)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)

    implementation(libs.appcompat)
    implementation(libs.kotlin.immutable.collections)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt.navigation.compose)
}