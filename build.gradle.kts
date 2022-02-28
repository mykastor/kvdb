plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "ru.spbu.math-cs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.36")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}