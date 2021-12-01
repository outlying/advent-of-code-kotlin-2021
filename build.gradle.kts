plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.truth:truth:1.1.3")
}

tasks {

    wrapper {
        gradleVersion = "7.3"
    }
}
