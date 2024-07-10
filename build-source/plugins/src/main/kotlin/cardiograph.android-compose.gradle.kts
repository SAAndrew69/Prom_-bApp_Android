import com.android.build.gradle.BaseExtension

plugins {
    id("cardiograph.android-lib")
}

@Suppress("UnstableApiUsage")
configure<BaseExtension> {
    buildFeatures.compose = true

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}