import com.android.build.gradle.BaseExtension

plugins{
    id("com.android.application")
    kotlin("android")
}

configure<BaseExtension> {
    commonAndroid(project)

    defaultConfig {
        applicationId = ApkConfig.APPLICATION_ID
    }

    buildTypes{
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
            consumerProguardFile("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        internal {
            isShrinkResources = true
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isShrinkResources = true
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}