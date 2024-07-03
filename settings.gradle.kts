pluginManagement {
    includeBuild("build-source")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Cardiograph"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

include(":components:singleactivity:api")
include(":components:singleactivity:impl")

include(":components:core:navigation")
include(":components:core:bluetooth")
include(":components:core:storage")
include(":components:core:network")
include(":components:core:notification")
include(":components:core:util")

include(":components:core:ui:topbar")
include(":components:core:ui:cardiogram")
include(":components:core:ui:theme")

include(":components:authorization:api")
include(":components:authorization:impl")

include(":components:scanner:api")
include(":components:scanner:impl")

include(":components:bottombar:api")
include(":components:bottombar:impl")

include(":components:measurement:api")
include(":components:measurement:impl")

include(":components:profile:api")
include(":components:deeplink:api")
