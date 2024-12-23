package kz.witme.project.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinAndroid(extension: LibraryExtension) = extension.apply {
    val moduleName = path
        .split(":")
        .drop(2)
        .joinToString(".")

    val newName = if (moduleName.contains("-")) moduleName.replace("-", "_") else moduleName

    namespace = if (moduleName.isNotEmpty()) "kz.witme.project.$newName" else "kz.witme.project"
    compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
    defaultConfig {
        minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
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

internal fun Project.configureServiceKotlinMultiplatform(extension: KotlinMultiplatformExtension) =
    extension.apply {
        jvmToolchain(17)
        androidTarget()
        iosArm64()
        iosX64()
        iosSimulatorArm64()
        applyDefaultHierarchyTemplate()

        sourceSets.apply {
            androidMain {
                dependencies {
                    implementation(libs.findLibrary("ktor-client-okhttp").get())
                }
            }
            iosMain {
                dependencies {
                    implementation(libs.findLibrary("ktor-client-darwin").get())
                }
            }
            commonMain {
                dependencies {
                    implementation(libs.findLibrary("ktor-client-core").get())
                    implementation(libs.findLibrary("ktor-client-content-negotiation").get())
                    implementation(libs.findLibrary("ktor-client-logging").get())
                    implementation(libs.findLibrary("ktor-serialization-kotlinx-json").get())
                    implementation(libs.findLibrary("ktor-client-auth").get())
                    implementation(libs.findLibrary("ktorfit").get())
                    implementation(libs.findLibrary("koin-core").get())
                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                    implementation(libs.findLibrary("kotlinx-immutable").get())
                    implementation(libs.findLibrary("kotlinx-serialization-json").get())
                    implementation(libs.findLibrary("datastore-preferences").get())
                    implementation(libs.findLibrary("datastore").get())
                    implementation(project(":core:common"))
                    implementation(project(":core:data"))
                }
            }
            androidMain {
                dependencies {
                    implementation(libs.findLibrary("koin-core").get())
                }
            }
        }
    }

internal fun Project.configureFeatureComposeMultiplatform(extension: KotlinMultiplatformExtension) =
    extension.apply {
        jvmToolchain(17)
        androidTarget()
        iosArm64()
        iosX64()
        iosSimulatorArm64()
        applyDefaultHierarchyTemplate()

        val compose = extensions.getByType<ComposeExtension>()

        sourceSets.apply {
            commonMain {
                dependencies {
                    implementation(compose.dependencies.runtime)
                    implementation(compose.dependencies.foundation)
                    implementation(compose.dependencies.material3)
                    implementation(compose.dependencies.material)
                    implementation(compose.dependencies.materialIconsExtended)
                    implementation(compose.dependencies.components.resources)
                    implementation(compose.dependencies.components.uiToolingPreview)
                    implementation(compose.dependencies.ui)

                    implementation(libs.findLibrary("koin.core").get())
                    implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                    implementation(libs.findLibrary("kotlinx.serialization.json").get())
                    implementation(libs.findLibrary("kotlinx.immutable").get())
                    implementation(libs.findLibrary("coil-compose").get())
                    implementation(libs.findLibrary("coil-compose-core").get())
                    implementation(libs.findLibrary("coil-network-ktor2").get())
                    implementation(libs.findLibrary("coil-network-ktor3").get())
                    implementation(libs.findLibrary("coil-mp").get())
                    implementation(libs.findLibrary("androidx.lifecycle.runtime.compose").get())

                    implementation(project(":core:common"))
                    implementation(project(":core:common-ui"))
                    implementation(project(":core:data"))
                    implementation(project(":core:navigation"))
                }
            }
            androidMain {
                dependencies {
                    implementation(libs.findLibrary("koin.android").get())
                }
            }
        }
    }