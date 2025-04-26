plugins {
    alias(libs.plugins.feature.module)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.service.book)
        }
    }
}