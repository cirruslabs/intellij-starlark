package org.cirruslabs.intellij.starlark.modules

import com.intellij.icons.AllIcons
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.AdditionalLibraryRootsProvider
import com.intellij.openapi.roots.JavaSyntheticLibrary
import com.intellij.openapi.roots.SyntheticLibrary
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.util.ResourceUtil
import com.jetbrains.python.psi.PyFile
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.Icon

class CirrusModuleManager : AdditionalLibraryRootsProvider() {
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
        VfsUtil.findFile(modulePath(locator), false)?.findFileByRelativePath(locator.path ?: "lib.star")
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

  override fun getAdditionalProjectLibraries(project: Project): Collection<SyntheticLibrary> {
    return listOf(StarlarkSyntheticLibrary(getRootsToWatch(project).toList()))
  }

  override fun getRootsToWatch(project: Project): Collection<VirtualFile> {
    return setOfNotNull(CIRRUS_MODULE?.parent, VfsUtil.findFile(globalCacheLocation, false))
  }
}

class StarlarkSyntheticLibrary(
  sourceRoots: List<VirtualFile>,
) : JavaSyntheticLibrary("Cirrus Modules", sourceRoots, emptyList(), emptySet()), ItemPresentation {
  override fun getPresentableText(): String? {
    return "Cirrus Modules"
  }

  override fun getIcon(unused: Boolean): Icon {
    return AllIcons.Ide.Rating
  }
}
