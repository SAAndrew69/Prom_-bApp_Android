plugins {
    id("cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.network"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.timber)
}