package targets

import com.android.build.gradle.LibraryExtension
import libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureAndroidLibrary(extension: LibraryExtension) {
    with(extension) {
        val moduleName = path
            .split(":")
            .drop(2)
            .joinToString(".")

        val newName = if (moduleName.contains("-")) {
            moduleName.replace("-", "_")
        } else {
            moduleName
        }

        namespace = "kz.witme.project.$newName"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        defaultConfig {
            minSdk = libs.versions.android.minSdk.get().toInt()
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}

internal fun configureAndroidTarget(extension: KotlinMultiplatformExtension) {
    with(extension) {
        jvmToolchain(17)
        androidTarget()
        applyDefaultHierarchyTemplate()
    }
}