// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()

    }

    dependencies {

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.8.10-1.0.9")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false

}