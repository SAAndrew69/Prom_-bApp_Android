plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation(libs.compose.gradle)
    implementation(libs.compose.compiler)
    implementation(libs.protobuf.gradle)
    implementation(libs.ksp.gradle)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}