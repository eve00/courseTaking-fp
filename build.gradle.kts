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

    implementation(platform("org.http4k:http4k-bom:5.12.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-format-jackson")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

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