plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    implementation("com.google.truth:truth:1.1.3")
}

tasks {

    wrapper {
        gradleVersion = "7.3"
    }
}
