plugins {
    id("gelab.cardiograph.android-lib")
    kotlin("kapt")
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

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}