plugins {
    id("cardiograph.android-app")
    id("cardiograph.hilt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "tech.gelab.cardiograph"
}

dependencies {
    // Project
    implementation(projects.components.core.notification)

    implementation(projects.components.core.bluetooth)
    implementation(projects.components.core.network)

    implementation(projects.components.core.ui.navigation)
    implementation(projects.components.core.ui.cardiogram)
    implementation(projects.components.core.ui.topbar)
    implementation(projects.components.core.ui.dialog)
    implementation(projects.components.core.ui.theme)

    implementation(projects.components.core.storage)
    implementation(projects.components.core.util)

    implementation(projects.components.bridge.api)
    implementation(projects.components.bridge.impl)

    implementation(projects.components.singleactivity.api)
    implementation(projects.components.singleactivity.impl)

    implementation(projects.components.authorization.api)
    implementation(projects.components.authorization.impl)
    implementation(projects.components.authorization.login)
    implementation(projects.components.authorization.signup)
    implementation(projects.components.authorization.skip)

    implementation(projects.components.pairing.api)
    implementation(projects.components.pairing.impl)

    implementation(projects.components.bottombar.api)
    implementation(projects.components.bottombar.impl)

    implementation(projects.components.idpicker.api)
    implementation(projects.components.idpicker.impl)

    implementation(projects.components.measurement.api)
    implementation(projects.components.measurement.impl)
    implementation(projects.components.measurement.progressnavigation)

    implementation(projects.components.reports.api)
    implementation(projects.components.reports.impl)

    implementation(projects.components.profile.api)
    implementation(projects.components.profile.impl)

    // Libs
    implementation(libs.appcompat)
    implementation(libs.timber)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}