plugins {
    id("gelab.cardiograph.android-compose")
}

android {
    namespace = "tech.gelab.cardiograph.bottombar.impl"
}

dependencies {
    implementation(projects.components.bottombar.api)

    implementation(libs.appcompat)

    
}