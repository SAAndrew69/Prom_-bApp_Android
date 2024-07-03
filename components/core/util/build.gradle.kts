plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.core.util"
}

dependencies {
    implementation(libs.appcompat)
}