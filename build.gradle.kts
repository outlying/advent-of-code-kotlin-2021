plugins {
    kotlin("jvm") version "1.6.0"
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
    implementation("com.google.truth:truth:1.1.3")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}

tasks {

    wrapper {
        gradleVersion = "7.3"
    }
}
