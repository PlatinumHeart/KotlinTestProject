import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

allprojects {
    println("allprojects each is: $this")

    repositories {
        mavenCentral()
    }
}

subprojects {
    println("subprojects each is: $this")

    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    kotlin {
        jvmToolchain(17)
    }

    println("tasks sz is: ${tasks.size}")
    tasks.forEach {
        println("each task is: $it")
    }

    dependencies {
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
        implementation("com.google.code.gson:gson:2.8.6")
    }
}