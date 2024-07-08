plugins {
    id("gelab.cardiograph.android-compose")
    kotlin("kapt")
}

android {
    namespace = "tech.gelab.cardiograph.authorization.impl"
}

dependencies {
    implementation(projects.components.authorization.api)
    implementation(projects.components.authorization.login)
    implementation(projects.components.authorization.signup)
    implementation(projects.components.authorization.skip)
    implementation(projects.components.authorization.util)

    implementation(projects.components.core.network)
    implementation(projects.components.core.notification)
    implementation(projects.components.core.util)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.topbar)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)

    implementation(projects.components.scanner.api)

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