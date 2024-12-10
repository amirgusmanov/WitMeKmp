plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("feature-module") {
            id = "kz.witme.project.feature-module"
            implementationClass = "kz.witme.project.plugin.FeatureModulePlugin"
        }
        register("service-module") {
            id = "kz.witme.project.service-module"
            implementationClass = "kz.witme.project.plugin.ServiceModulePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly("com.android.tools.build:gradle:8.1.4")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    compileOnly("org.jetbrains.compose:org.jetbrains.compose.gradle.plugin:1.7.0")
}
