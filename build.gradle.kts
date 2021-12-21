plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "io.eliez.lab"
version = "1.0-SNAPSHOT"

val log4jVersion = "2.14.1"

application {
    mainClass.set("io.eliez.lab.log4jextra.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform("org.apache.logging.log4j:log4j-bom:$log4jVersion"))
    implementation("com.github.ajalt.clikt:clikt:3.3.0")

    implementation("org.apache.logging.log4j:log4j-api")

    runtimeOnly("org.apache.logging.log4j:log4j-core")
}
