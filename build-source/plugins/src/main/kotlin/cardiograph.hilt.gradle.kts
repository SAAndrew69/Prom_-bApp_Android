import org.gradle.kotlin.dsl.kotlin

plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    "implementation"(libs.hilt)
    "ksp"(libs.hilt.compiler)
}