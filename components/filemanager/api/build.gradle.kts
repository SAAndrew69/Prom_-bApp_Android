plugins {
    id("cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.filemanager.api"
}

dependencies {
    implementation(libs.appcompat)
}