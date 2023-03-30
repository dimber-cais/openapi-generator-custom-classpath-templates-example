plugins {
    id("java-library")
    id("maven-publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}

group = "com.example"
version = 1.0

publishing {
    publications {
        create<MavenPublication>(project.name) {
            artifactId = project.name
            from(components["java"])
        }
    }
}

