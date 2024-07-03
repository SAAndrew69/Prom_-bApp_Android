plugins {
    id("gelab.cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.ui.theme"
}

dependencies {
    implementation(libs.appcompat)

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
}