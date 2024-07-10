import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("kapt")
}

dependencies {
    "implementation"(libs.hilt)
    "kapt"(libs.hilt.compiler)
}