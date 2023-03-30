rootProject.name = "custom-classpath-templates-example"

pluginManagement {
    repositories {
        mavenLocal()
    }
}

include(
    "generator-module",
    "templates-module"
)
