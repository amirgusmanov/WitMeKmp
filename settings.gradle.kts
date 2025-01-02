rootProject.name = "WitMeKmp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("core/plugin")
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":core:common")
include(":core:common-ui")
include(":core:data")
include(":core:navigation")
include(":feature:auth")
include(":service:auth")
include(":service:profile")
include(":feature:dashboard")
include(":service:book")
include(":feature:create-book")
include(":feature:timer")
include(":feature:profile")
include(":feature:book-details")
include(":feature:splash")
