package com.cevichepicante.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import kotlin.text.get

internal fun Project.configureAndroidCompose(
    extension: CommonExtension<*, *, *, *, *, *>
) {
    extension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            "implementation"(platform(bom))
            "implementation"(libs.findLibrary("androidx-material3").get())
            "implementation"(libs.findLibrary("androidx-ui-graphics").get())
            "implementation"(libs.findLibrary("androidx-ui-tooling-preview").get())

            // TODO needed?
            "androidTestImplementation"(platform(bom))
            "debugImplementation"(libs.findLibrary("androidx-ui-tooling").get())
        }
    }
}