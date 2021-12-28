import org.gradle.internal.logging.text.StyledTextOutput
import org.gradle.internal.logging.text.StyledTextOutputFactory
import org.gradle.kotlin.dsl.support.serviceOf

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

    implementation("org.springframework.boot:spring-boot-starter-logging:2.6.1") {
        if (project.hasProperty("log4shell")) {
            exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
        }
    }
    implementation("org.slf4j:slf4j-api")
    implementation("org.apache.logging.log4j:log4j-api")

    runtimeOnly("org.apache.logging.log4j:log4j-core")
}

tasks.named("run") {
    doLast {
        val logGroups = setOf("org.apache.logging.log4j", "org.slf4j", "ch.qos.logback")
        val log4jModules = configurations.runtimeClasspath.get().incoming.resolutionResult.allDependencies
            .asSequence()
            .filterIsInstance<ResolvedDependencyResult>()
            .map { it.selected.id }
            .filterIsInstance<ModuleComponentIdentifier>()
            .filter { it.group in logGroups }
            .map { it.toString() }
            .toSet()
        val out = serviceOf<StyledTextOutputFactory>().create("root")
        out.println()
        out.withStyle(StyledTextOutput.Style.Identifier).println("Logging modules in runtimeClasspath:")
        log4jModules.sorted().forEach {
            out.withStyle(StyledTextOutput.Style.Info).println("+--- $it")
        }
    }
}
