plugins {
    id("cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.ui.layout"
}

dependencies {
    implementation(projects.components.core.ui.ktx)
    implementation(projects.components.core.ui.theme)
    implementation(projects.components.core.ui.topbar)

    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
}