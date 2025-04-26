import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.applyServiceDependencies(extension: KotlinMultiplatformExtension) {
    with(extension) {
        sourceSets.apply {
            androidMain {
                dependencies {
                    implementation(libs.ktor.client.okhttp.get())
                }
            }
            iosMain {
                dependencies {
                    implementation(libs.ktor.client.darwin.get())
                }
            }
            commonMain {
                dependencies {
                    implementation(libs.ktor.client.core.get())
                    implementation(libs.ktor.client.content.negotiation.get())
                    implementation(libs.ktor.client.logging.get())
                    implementation(libs.ktor.serialization.kotlinx.json.get())
                    implementation(libs.ktor.client.auth.get())
                    implementation(libs.ktorfit.get())
                    implementation(libs.koin.core.get())
                    implementation(libs.kotlinx.coroutines.core.get())
                    implementation(libs.kotlinx.immutable.get())
                    implementation(libs.kotlinx.serialization.json.get())
                    implementation(libs.datastore.preferences.get())
                    implementation(libs.datastore.asProvider().get())
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

                    implementation(libs.koin.core.get())
                    implementation(libs.kotlinx.coroutines.core.get())
                    implementation(libs.kotlinx.serialization.json.get())
                    implementation(libs.kotlinx.immutable.get())
                    implementation(libs.coil.compose.asProvider().get())
                    implementation(libs.coil.compose.core.get())
                    implementation(libs.coil.network.ktor2.get())
                    implementation(libs.coil.network.ktor3.get())
                    implementation(libs.coil.mp.get())
                    implementation(libs.androidx.lifecycle.runtime.compose.get())

                    implementation(project(":core:common"))
                    implementation(project(":core:common-ui"))
                    implementation(project(":core:data"))
                    implementation(project(":core:navigation"))
                }
            }
            androidMain {
                dependencies {
                    implementation(libs.koin.android.get())
                }
            }
        }
    }
}