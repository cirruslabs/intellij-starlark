package org.cirruslabs.intellij.starlark.modules

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task.Backgroundable
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.platform.templates.github.DownloadUtil
import com.intellij.platform.templates.github.ZipUtil
import org.cirruslabs.intellij.starlark.StarlarkBundle
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipInputStream

class DownloadGitHubModuleQuickFix(val module: ModuleLocator) : LocalQuickFix {
  constructor(module: String) : this(ModuleLocator.parse(module))

  override fun getFamilyName(): String =
    "Download ${module.org}/${module.repo} module"

  override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
    val containingFile = descriptor.psiElement.containingFile
    ProgressManager.getInstance().run(object : Backgroundable(project, StarlarkBundle.getMessage("starlark.intention.fetch.progress.fetching.module")) {
      override fun run(indicator: ProgressIndicator) {
        val moduleURL = "https://github.com/${module.org}/${module.repo}/archive/${module.revision ?: "main"}.zip"
        val modulePath = CirrusModuleManager.modulePath(module)

        val tmpFile = File.createTempFile("download", "module")
        tmpFile.deleteOnExit()
        try {
          DownloadUtil.downloadAtomically(indicator, moduleURL, tmpFile)
          FileUtil.delete(modulePath)
          ZipUtil.unzip(indicator, modulePath, ZipInputStream(BufferedInputStream(FileInputStream(tmpFile))), null, null, true)
        } finally {
          tmpFile.delete()
        }

        // refresh VFS so the files are picked up before requesting a code analyzer to restart
        VirtualFileManager.getInstance().refreshAndFindFileByNioPath(modulePath.resolve(module.path ?: "lib.star"))
        ApplicationManager.getApplication().invokeLater {
          DaemonCodeAnalyzer.getInstance(project).restart(containingFile)
        }
      }
    })
  }
}
