plugins {
    id("cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.idpicker.api"
}

dependencies {
    implementation(projects.components.core.ui.navigation)

    implementation(libs.appcompat)
}