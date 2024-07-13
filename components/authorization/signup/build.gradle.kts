plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.authorization.signup"
}

dependencies {
    implementation(projects.components.authorization.api)
    implementation(projects.components.authorization.util)

    implementation(projects.components.bottombar.api)

    implementation(projects.components.core.network)
    implementation(projects.components.core.notification)
    implementation(projects.components.core.util)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.topbar)
    implementation(projects.components.core.ui.dialog)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)

    implementation(projects.components.pairing.api)

    implementation(libs.appcompat)
    implementation(libs.timber)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt.navigation.compose)
}