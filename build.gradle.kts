plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.allopen") version "2.0.20"

    // The following line allows to load io.gatling.gradle plugin and directly apply it
    id("io.gatling.gradle") version "3.12.0.2"
}

gatling {
    enterprise.closureOf<Any> {
        // Enterprise Cloud (https://cloud.gatling.io/) configuration reference: https://docs.gatling.io/reference/integrations/build-tools/gradle-plugin/
    }
}

repositories {
    mavenCentral()
}
