plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("feature-module") {
            id = "feature-module"
            implementationClass = "FeatureModulePlugin"
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
}
