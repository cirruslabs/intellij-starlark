import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.9.22"
  id("org.jetbrains.intellij") version "1.17.4"
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
  pluginName.set("Starlark")
  version.set("2024.1.7")
  plugins.set(listOf("PythonCore:241.18034.55", "com.intellij.java", "Git4Idea"))
  downloadSources.set(true)
  updateSinceUntilBuild.set(false) // let's leave until open ended
}

tasks {
  patchPluginXml {
    version.set(System.getenv().getOrDefault("CIRRUS_TAG", "2.0-SNAPSHOT"))
  }

  publishPlugin {
    token.set(System.getenv("JETBRAINS_TOKEN"))
  }
}
