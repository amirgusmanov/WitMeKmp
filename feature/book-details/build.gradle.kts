plugins {
    id("kz.witme.project.feature-module")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.service.book)
        }
    }
}