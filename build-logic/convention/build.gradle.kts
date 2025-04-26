plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("feature-module") {
            id = "feature-module"
            implementationClass = "plugins.FeatureModulePlugin"
        }
        register("service-module") {
            id = "service-module"
            implementationClass = "plugins.ServiceModulePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
