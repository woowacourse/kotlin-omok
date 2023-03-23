// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val agpVersion = "7.4.0"
    id("com.android.application") version agpVersion apply false
    id("com.android.library") version agpVersion apply false

    val kotlinVersion = "1.8.10"
    kotlin("android") version "1.7.10" apply false
    kotlin("jvm") version kotlinVersion apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
