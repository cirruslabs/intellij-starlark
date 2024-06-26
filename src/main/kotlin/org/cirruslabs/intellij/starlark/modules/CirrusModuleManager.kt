package org.cirruslabs.intellij.starlark.modules

import com.intellij.openapi.application.PathManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.util.ResourceUtil
import com.intellij.util.indexing.IndexableSetContributor
import com.jetbrains.python.psi.PyFile
import java.nio.file.Path
import java.nio.file.Paths

class CirrusModuleManager : IndexableSetContributor() {
  companion object {
    private val CIRRUS_MODULE by lazy {
      val url = ResourceUtil.getResource(javaClass.classLoader, "modules", "cirrus.star")
      VfsUtil.findFileByURL(url)
    }

    val CIRRUS_MODULE_HOME: VirtualFile? by lazy {
      CIRRUS_MODULE?.parent
    }

    fun findModule(module: String): VirtualFile? {
      if (module == "cirrus") return CIRRUS_MODULE
      return try {
        val locator = ModuleLocator.parse(module)
        VfsUtil.findFile(modulePath(locator), false)?.findChild(locator.path ?: "lib.star")
      } catch (ex: IllegalStateException) {
        null
      }
    }

    fun resolveModuleFile(file: PsiFile, module: String): PyFile? {
      val originalFile = file.originalFile
      val moduleFile = findModule(module) ?: originalFile.virtualFile?.parent?.findFileByRelativePath(module)
      return moduleFile?.let {
        PsiManager.getInstance(originalFile.project).findFile(it) as? PyFile
      }
    }

    internal val globalCacheLocation: Path
      get() = Paths.get(PathManager.getSystemPath(), "externalStarlarkModules")

    fun modulePath(module: ModuleLocator): Path =
      globalCacheLocation.resolve(module.org)
        .resolve(module.repo)
        .resolve(module.revision ?: "main")
  }

  override fun getAdditionalRootsToIndex(): Set<VirtualFile> {
    return setOfNotNull(CIRRUS_MODULE?.parent, VfsUtil.findFile(globalCacheLocation, false))
  }
}
