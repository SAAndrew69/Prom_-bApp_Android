plugins {
    id("cardiograph.android-lib")
    id("cardiograph.hilt")
}

android {
    namespace = "tech.gelab.cardiograph.filemanagerimpl"
}

dependencies {
    implementation(projects.components.filemanager.api)

    implementation(libs.appcompat)
}