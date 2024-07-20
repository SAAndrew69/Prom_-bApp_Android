plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "tech.gelab.cardiograph.singleactivity.impl"
}

dependencies {
    implementation(projects.components.core.network)

    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.navigation)

    implementation(projects.components.authorization.api)
    implementation(projects.components.bottombar.api)
    implementation(projects.components.pairing.api)

    implementation(libs.appcompat)
    implementation(libs.kotlin.immutable.collections)
    implementation(libs.timber)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
}