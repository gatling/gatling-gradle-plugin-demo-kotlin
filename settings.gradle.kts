pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "2.2.21"
        kotlin("plugin.allopen") version "2.2.21"
        id("io.gatling.gradle") version "3.14.9"
    }
}