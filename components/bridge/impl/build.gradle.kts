plugins {
    id("gelab.cardiograph.android-lib")
    kotlin("kapt")
}

android {
    namespace = "tech.gelab.cardiograph.bridge.impl"
}

dependencies {
    implementation(projects.components.bridge.api)
    implementation(projects.components.core.bluetooth)

    implementation(libs.appcompat)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}