plugins {
    id("cardiograph.android-lib")
    id("cardiograph.hilt")
    id("cardiograph.protobuf")
}

android {
    namespace = "tech.gelab.cardiograph.storage"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.datastore)
}