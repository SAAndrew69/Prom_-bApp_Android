plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.conclusion.impl"
}

dependencies {
    implementation(projects.components.conclusion.api)
    implementation(projects.components.measurement.api)

    implementation(projects.components.core.ui.ktx)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.topbar)
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)

    implementation(libs.hilt.navigation.compose)
}