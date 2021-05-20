import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.intellij.tasks.PublishTask
import org.jetbrains.intellij.tasks.RunIdeTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.32"
  id("org.jetbrains.intellij") version "0.7.2"
}

tasks.withType<JavaCompile> {
  sourceCompatibility = "1.8"
  targetCompatibility = "1.8"
}
listOf("compileKotlin", "compileTestKotlin").forEach {
  tasks.getByName<KotlinCompile>(it) {
    kotlinOptions.jvmTarget = "1.8"
  }
}

repositories {
  mavenCentral()
}

intellij {
  version = "2021.1.1"
  setPlugins("PythonCore:211.7142.45", "com.intellij.java")
  isDownloadSources = true
  pluginName = "Starlark"
}

val patchPluginXml: PatchPluginXmlTask by tasks
patchPluginXml.setVersion(System.getenv().getOrDefault("CIRRUS_TAG", "2.0-SNAPSHOT"))

val publishPlugin: PublishTask by tasks
publishPlugin.setToken(System.getenv().getOrDefault("JETBRAINS_TOKEN", ""))

val runIde: RunIdeTask by tasks
runIde.systemProperty("idea.is.internal", "true")
