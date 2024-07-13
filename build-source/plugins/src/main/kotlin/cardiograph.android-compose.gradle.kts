import com.android.build.gradle.BaseExtension

plugins {
    id("cardiograph.android-lib")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

configure<BaseExtension> {
    buildFeatures.compose = true
}