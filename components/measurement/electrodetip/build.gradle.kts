plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.measurement.electrodetip"
}

dependencies {
    implementation(projects.components.measurement.api)

    implementation(projects.components.core.storage)

    implementation(projects.components.core.ui.ktx)
    implementation(projects.components.core.ui.dialog)
    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.theme)

    implementation(libs.appcompat)
    implementation(libs.datastore)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)

    implementation(libs.hilt.navigation.compose)
}