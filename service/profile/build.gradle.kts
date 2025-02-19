plugins {
    id("kz.witme.project.service-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.service.profileRuntimeStorage)
        }
    }
}