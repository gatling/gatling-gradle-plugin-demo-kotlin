// build.gradle.kts
plugins {
    kotlin("jvm")
    kotlin("plugin.allopen")
    id("io.gatling.gradle")
}

repositories { mavenCentral() }

dependencies {
    // For code in src/gatling/... use the gatling source set configuration
    gatlingImplementation("com.michael-bull.kotlin-inline-logger:kotlin-inline-logger-jvm:1.0.6")
}

gatling { }