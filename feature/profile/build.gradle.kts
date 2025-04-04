plugins {
    id("kz.witme.project.feature-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.service.profile)
            implementation(projects.service.profileRuntimeStorage)
            implementation(projects.service.auth)
        }
    }
}