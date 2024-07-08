plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.bridge.api"
}

dependencies {
    implementation(libs.appcompat)
}