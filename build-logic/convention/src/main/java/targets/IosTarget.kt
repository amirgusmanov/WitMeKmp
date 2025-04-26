package targets

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun configureIosTarget(extension: KotlinMultiplatformExtension) {
    with(extension) {
        iosArm64()
        iosX64()
        iosSimulatorArm64()
    }
}