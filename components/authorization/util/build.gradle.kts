plugins {
    id("cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.authorization.util"
}

dependencies {
    implementation(projects.components.core.ui.ktx)
    implementation(projects.components.core.ui.theme)

    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.navigation)
    implementation(libs.compose.material3)
}