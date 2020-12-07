buildscript {
    repositories {
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("me.champeau.gradle:jmh-gradle-plugin:0.5.2")
    }
}

plugins {
    kotlin("jvm") version "1.4.20"
    id("me.champeau.gradle.jmh") version "0.5.2"
    application
}

application {
    mainClass.set("y2020.day07.MainKt")
    // applicationDefaultJvmArgs = listOf("-Xmx3072m")
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "y2020.day07.MainKt"
    }
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

val jmhExclude: String? by project
val jmhInclude: String? by project

jmh {
    benchmarkMode = listOf("sample")
    if (!jmhExclude.isNullOrEmpty()) exclude = listOf(jmhExclude)
    if (!jmhInclude.isNullOrEmpty()) include = listOf(jmhInclude)
    duplicateClassesStrategy = DuplicatesStrategy.WARN
    fork = 1
    threads = 1
    timeOnIteration = "1s"
    timeUnit = "ms"
    warmupIterations = 1
}