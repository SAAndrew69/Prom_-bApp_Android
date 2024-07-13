plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    "implementation"(libs.hilt)
    "ksp"(libs.hilt.compiler)
}