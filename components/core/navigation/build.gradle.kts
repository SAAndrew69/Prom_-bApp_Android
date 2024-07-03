plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.core.navigation"
}

dependencies {

    implementation(libs.appcompat)

    implementation(libs.compose.navigation)

}