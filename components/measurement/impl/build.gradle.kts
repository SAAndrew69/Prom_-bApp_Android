plugins {
    id("gelab.cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.measurement.impl"
}

dependencies {
    implementation(projects.components.measurement.api)

    implementation(libs.appcompat)
}