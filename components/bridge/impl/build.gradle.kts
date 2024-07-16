plugins {
    id("cardiograph.android-lib")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.bridge.impl"
}

dependencies {
    implementation(projects.components.bridge.api)
    implementation(projects.components.core.bluetooth)

    implementation(libs.appcompat)
    implementation(libs.timber)

    implementation(libs.ble)
    implementation(libs.ble.scan)
    implementation(libs.ble.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}