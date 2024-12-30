plugins {
    id("kz.witme.project.feature-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.service.auth)
            implementation(projects.service.profile)
        }
    }
}
