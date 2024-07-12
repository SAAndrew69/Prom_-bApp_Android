plugins {
    id("cardiograph.android-compose")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.bottombar.impl"
}

dependencies {
    implementation(projects.components.bottombar.api)

    implementation(projects.components.core.storage)

    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.topbar)
    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.ktx)

    implementation(libs.timber)
    implementation(libs.appcompat)
    implementation(libs.datastore)
    implementation(libs.kotlin.immutable.collections)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    implementation(libs.hilt.navigation.compose)
}