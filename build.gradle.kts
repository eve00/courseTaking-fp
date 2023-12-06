plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.8.20"

    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation ("org.http4k:http4k-core")
    implementation( "org.http4k:http4k-server-jetty:4.48.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")


    testImplementation ("org.http4k:http4k-client-jetty:4.48.0.0")
    testImplementation( "org.jsoup:jsoup:1.12.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("Main")
}