plugins {
    id("gelab.cardiograph.android-lib")
}

android {
    namespace = "tech.gelab.cardiograph.storage"
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.datastore.preferences)

}