plugins {
    kotlin("jvm") version "1.4.32"
    id("org.jetbrains.intellij") version "0.7.2"
}

repositories {
    mavenCentral()
}

intellij {
    version = "2021.1"
    setPlugins("PythonCore:211.6693.119")
    isDownloadSources = true
    pluginName = "Starlark"
}
