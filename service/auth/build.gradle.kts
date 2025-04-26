plugins {
    alias(libs.plugins.service.module)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.service.profileRuntimeStorage)
        }
    }
}