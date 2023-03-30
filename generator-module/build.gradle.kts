plugins {
    id("org.openapi.generator") version "6.5.0-SNAPSHOT"
}

repositories {
    mavenCentral()
    mavenLocal()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("com.example:templates-module:1.0")
    }
}

openApiGenerate {
    generatorName.set("kotlin")
    library.set("jvm-retrofit2")
    packageName.set("com.example")
    inputSpec.set("${project.projectDir}/spec.yaml")
    generateApiDocumentation.set(false)
    generateApiTests.set(false)
    generateModelDocumentation.set(false)
    generateModelTests.set(false)
    outputDir.set("$buildDir/generated/sources/client")
    templateResourcePath.set("custom-templates")
}
