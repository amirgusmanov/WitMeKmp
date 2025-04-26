import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.applyServiceDependencies(extension: KotlinMultiplatformExtension) {
    with(extension) {
        sourceSets.apply {
            androidMain {
                dependencies {
                    implementation(libs.ktor.client.okhttp)
                }
            }
            iosMain {
                dependencies {
                    implementation(libs.ktor.client.darwin)
                }
            }
            commonMain {
                dependencies {
                    implementation(libs.bundles.ktor)
                    implementation(libs.ktorfit)
                    implementation(libs.koin.core)
                    implementation(libs.kotlinx.coroutines.core)
                    implementation(libs.kotlinx.immutable)
                    implementation(libs.kotlinx.serialization.json)
                    implementation(project(":core:common"))
                    implementation(project(":core:data"))
                }
            }
        }
    }
}

internal fun Project.applyFeatureDependencies(extension: KotlinMultiplatformExtension) {
    with(extension) {
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

                    implementation(libs.koin.core)
                    implementation(libs.kotlinx.coroutines.core)
                    implementation(libs.kotlinx.serialization.json)
                    implementation(libs.kotlinx.immutable)
                    implementation(libs.bundles.coil)
                    implementation(libs.androidx.lifecycle.runtime.compose)

                    implementation(project(":core:common"))
                    implementation(project(":core:common-ui"))
                    implementation(project(":core:data"))
                    implementation(project(":core:navigation"))
                }
            }
            androidMain {
                dependencies {
                    implementation(libs.koin.android)
                }
            }
        }
    }
}