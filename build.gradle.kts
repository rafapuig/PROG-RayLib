plugins {
    id("java")
}

group = "es.rafapuig"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    plugins.withId("java") {
        dependencies {
            implementation("uk.co.electronstudio.jaylib:jaylib:6.0.+")
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        dependencies {
            add("implementation", "uk.co.electronstudio.jaylib:jaylib:6.0.+")
        }
    }
}


tasks.test {
    useJUnitPlatform()
}