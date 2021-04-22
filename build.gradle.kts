import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.intellij.tasks.PublishTask

plugins {
  kotlin("jvm") version "1.4.32"
  id("org.jetbrains.intellij") version "0.7.2"
}

repositories {
  mavenCentral()
}

intellij {
  version = "2021.1"
  setPlugins("PythonCore:211.6693.119", "com.intellij.java")
  isDownloadSources = true
  pluginName = "Starlark"
}

val patchPluginXml: PatchPluginXmlTask by tasks
patchPluginXml.setVersion(System.getenv().getOrDefault("CIRRUS_TAG", "1.0"))

val publishPlugin: PublishTask by tasks
publishPlugin.setToken(System.getenv().getOrDefault("JETBRAINS_TOKEN", ""))
