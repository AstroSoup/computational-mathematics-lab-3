plugins {
    id("java")
}

group = "ru.astrosoup"
version = "0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.astrosoup.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}