plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.profile.impl"
}

dependencies {
    implementation(projects.components.profile.api)

    implementation(projects.components.authorization.api)

    implementation(projects.components.core.notification)
    implementation(projects.components.core.storage)
    implementation(projects.components.core.util)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)

    implementation(libs.appcompat)
    implementation(libs.datastore)
    implementation(libs.timber)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt.navigation.compose)
}