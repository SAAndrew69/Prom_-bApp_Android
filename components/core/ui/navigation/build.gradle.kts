plugins {
    id("gelab.cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.core.ui.navigation"
}

dependencies {

    implementation(libs.appcompat)

    implementation(libs.timber)

    implementation(libs.kotlin.immutable.collections)

    implementation(libs.compose.navigation)
}