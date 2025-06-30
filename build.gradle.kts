plugins {
    kotlin("jvm") version "2.1.21"
    `java-library`
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val buildversion: String = if (project.hasProperty("version")) {
    project.property("version") as String
} else {
    "unspecified"
}

group = "alepando.dev"
version = buildversion

repositories {
    mavenCentral()
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.citizensnpcs.co/repo") { name = "citizens-repo" }
    maven("https://repo.opencollab.dev/main/") { name = "opencollab-snapshot" }
}

dependencies {
    paperweight.paperDevBundle("1.21.6-R0.1-SNAPSHOT")
    implementation(platform("com.intellectualsites.bom:bom-newest:1.52"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(project(":dialog-api"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks {
    shadowJar {
        mustRunAfter(":dialog-api:shadowJar")
        archiveClassifier.set("")
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("1.21.6")
    }
}
