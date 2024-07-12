plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.idpicker.impl"
}

dependencies {
    implementation(projects.components.idpicker.api)
    implementation(projects.components.bridge.api)
    implementation(projects.components.pairing.api)
    implementation(projects.components.measurement.api)

    implementation(projects.components.core.storage)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.ktx)

    implementation(libs.appcompat)
    implementation(libs.datastore)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)

    implementation(libs.hilt.navigation.compose)
}