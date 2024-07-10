plugins {
    id("cardiograph.android-lib")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.bluetooth"
}

dependencies {
    implementation(libs.appcompat)

    implementation(libs.ble)
    implementation(libs.ble.ktx)
    implementation(libs.ble.scan)

    implementation(libs.timber)
}