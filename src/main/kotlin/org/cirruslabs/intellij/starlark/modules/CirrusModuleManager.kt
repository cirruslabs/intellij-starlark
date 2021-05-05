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
import kotlin.io.path.Path

class CirrusModuleManager : IndexableSetContributor() {
    companion object {
        private val CIRRUS_MODULE by lazy {
            val url = ResourceUtil.getResource(javaClass.classLoader, "modules", "cirrus.star")
            VfsUtil.findFileByURL(url)
        }

        fun findModule(module: String): VirtualFile? {
            if (module == "cirrus") return CIRRUS_MODULE
            return try {
                val locator = ModuleLocator.parse(module)
                VfsUtil.findFile(modulePath(locator), true)?.findChild(locator.path ?: "lib.star")
            } catch (ex: IllegalStateException) {
                null
            }
        }

        fun resolveModuleFile(originalFile: PsiFile, module: String): PyFile? {
            return findModule(module)?.let {
                PsiManager.getInstance(originalFile.project).findFile(it) as? PyFile
            }
        }

        private val globalCacheLocation: Path
            get() = Paths.get(PathManager.getSystemPath(), "extStarlarkModules")

        fun modulePath(module: ModuleLocator): Path = globalCacheLocation.resolve(module.org).resolve(module.repo)
    }

    override fun getAdditionalRootsToIndex(): Set<VirtualFile> {
        return setOfNotNull(CIRRUS_MODULE?.parent, VfsUtil.findFile(globalCacheLocation, true))
    }
}