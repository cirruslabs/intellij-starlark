package org.cirruslabs.intellij.starlark.modules

data class ModuleLocator(
  val org: String,
  val repo: String,
  val path: String? = null,
  val revision: String? = null
) {
  companion object {
    private val regex = Regex("^(github\\.com/(?<org>.+?)?/(?<repo>.+?)?)(/(?<path>.+?))?(@(?<revision>.+?))?$")

    fun parse(module: String): ModuleLocator {
      val match = regex.find(module)
        ?: throw IllegalStateException("Can't parse module $module!")
      return ModuleLocator(
        (match.groups["org"] ?: throw IllegalStateException("Can't parse org!")).value,
        (match.groups["repo"] ?: throw IllegalStateException("Can't parse repo!")).value,
        match.groups["path"]?.value,
        match.groups["revision"]?.value,
      )
    }
  }
}
