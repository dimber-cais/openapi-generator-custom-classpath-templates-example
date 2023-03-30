# OpenAPI Generator Gradle Plugin - Example of loading templates from classpath

This accompanies PR: https://github.com/OpenAPITools/openapi-generator/pull/14909

### Build the PR branch of the gradle plugin
It is expected that prior to running the below steps, you have first run:
```shell
$ git clone git@github.com:dimber-cais/openapi-generator.git openapi-generator-custom-templates
$ cd openapi-generator-custom-templates
$ git checkout feature/gradle-classpath-templates
$ cd modules/openapi-generator-gradle-plugin
$ /gradlew publishToMavenLocal
```
This will publish version `6.5.0-SNAPSHOT` of the gradle plugin to your Maven local repository.

### Use the local version of the gradle plugin
The root project is setup to utilize Gradle plugins within the local Maven 
repository. This is achieved via the following in the `settings.gradle.kts` file:
```kotlin
pluginManagement {
    repositories {
        mavenLocal()
    }
}
```

### Publish a JAR containing customized templates

```shell
$ cd templates-module
$ gradle publishToMavenLocal
```
This will build the `templates-module` as a JAR and publish it to the local
Maven repository. The JAR includes file `custom-templates/README.mustache` which
will override the default template with this custom template.

### Generate using customized templates from the classpath

The `generator-module` is setup to generate as:

```kotlin
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
```
The key setting here is `templateResourcePath.set("custom-templates")` which
will cause the generate task to use the custom templates.

We also configure it to ensure that the custom templates JAR is available to the plugin's classpath:
```kotlin
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("com.example:templates-module:1.0")
    }
}
```

We can now execute the generate task:
```shell
$ cd generator-module
$ gradle openApiGenerate
```

We can see that the generated `README.md` has been changed to contain customized content:
```shell
$ cat build/generated/sources/client/README.md                                                                 
# CUSTOMIZED CLASSPATH TEMPLATE OUTPUT
```
