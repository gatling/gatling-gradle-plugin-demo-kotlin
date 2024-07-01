plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.allopen") version "1.9.24"

    // The following line allows to load io.gatling.gradle plugin and directly apply it
    id("io.gatling.gradle") version "3.11.5.1"
}

gatling {
    enterprise.closureOf<Any> {
        // Enterprise Cloud (https://cloud.gatling.io/) configuration reference: https://gatling.io/docs/gatling/reference/current/extensions/gradle_plugin/#working-with-gatling-enterprise-cloud
        // Enterprise Self-Hosted configuration reference: https://gatling.io/docs/gatling/reference/current/extensions/gradle_plugin/#working-with-gatling-enterprise-self-hosted
    }
}

repositories {
    mavenCentral()
}
