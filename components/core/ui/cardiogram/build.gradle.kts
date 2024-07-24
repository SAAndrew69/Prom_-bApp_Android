plugins {
    id("cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.ui.cardiogram"
}

dependencies {
    implementation(libs.kotlin.immutable.collections)
    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material3)

    implementation(projects.components.core.ui.theme)
}