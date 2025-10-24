pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(uri("https://jitpack.io"))
    }
}

rootProject.name = "WhatToEat"
include(":app")
include(":core")
include(":feature")
include(":core:ui")
include(":core:data")
include(":core:model")
include(":feature:foodpicker")
include(":feature:recipe")
include(":feature:order")
include(":core:common")
include(":core:database")
