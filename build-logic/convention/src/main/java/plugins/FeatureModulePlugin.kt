package plugins

import applyFeatureDependencies
import com.android.build.gradle.LibraryExtension
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import targets.configureAndroidLibrary
import targets.configureAndroidTarget
import targets.configureIosTarget

class FeatureModulePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.kotlinMultiplatform.get().pluginId)
            apply(libs.plugins.androidLibrary.get().pluginId)
            apply(libs.plugins.composeMultiplatform.get().pluginId)
            apply(libs.plugins.composeCompiler.get().pluginId)
        }
        extensions.configure<KotlinMultiplatformExtension>(::applyFeatureDependencies)
        extensions.configure<KotlinMultiplatformExtension>(::configureIosTarget)
        extensions.configure<KotlinMultiplatformExtension>(::configureAndroidTarget)
        extensions.configure<LibraryExtension>(::configureAndroidLibrary)
    }
}