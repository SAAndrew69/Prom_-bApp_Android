plugins {
    id("cardiograph.android-lib")
    id("cardiograph.hilt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "tech.gelab.cardiograph.room"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.ksp)
}