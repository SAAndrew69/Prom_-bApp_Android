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

include(":components:core:bluetooth")
include(":components:core:storage")
include(":components:core:network")
include(":components:core:notification")
include(":components:core:dialog")
include(":components:core:util")

include(":components:core:ui:navigation")
include(":components:core:ui:topbar")
include(":components:core:ui:cardiogram")
include(":components:core:ui:theme")
include(":components:core:ui:ktx")
include(":components:core:ui:dialog")
include(":components:core:ui:layout")

include(":components:authorization:api")
include(":components:authorization:impl")
include(":components:authorization:login")
include(":components:authorization:signup")
include(":components:authorization:skip")
include(":components:authorization:util")

include(":components:pairing:api")
include(":components:pairing:impl")

include(":components:bottombar:api")
include(":components:bottombar:impl")

include(":components:measurement:api")
include(":components:measurement:impl")
include(":components:measurement:electrodetip")

include(":components:idpicker:api")
include(":components:idpicker:impl")

include(":components:reports:api")
include(":components:reports:impl")

include(":components:profile:api")
include(":components:profile:impl")

include(":components:bridge:api")
include(":components:bridge:impl")

include(":components:deeplink:api")

include(":components:conclusion:api")
include(":components:conclusion:impl")

include(":components:filemanager:api")
include(":components:filemanager:impl")
include(":components:idpicker:newemployee")
include(":components:core:room")
