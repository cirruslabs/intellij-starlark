package org.cirruslabs.intellij.starlark.modules

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task.Backgroundable
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFileManager
import git4idea.checkout.GitCheckoutProvider
import git4idea.commands.Git
import git4idea.commands.GitCommand
import git4idea.commands.GitLineHandler
import org.cirruslabs.intellij.starlark.StarlarkBundle
import kotlin.io.path.name

class DownloadGitHubModuleQuickFix(private val module: ModuleLocator) : LocalQuickFix {
  constructor(module: String) : this(ModuleLocator.parse(module))

  override fun getFamilyName(): String =
    "Download ${module.org}/${module.repo} module"

  override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
    val containingFile = descriptor.psiElement.containingFile

    ProgressManager.getInstance().run(object : Backgroundable(project, StarlarkBundle.getMessage("starlark.intention.fetch.progress.fetching.module")) {
      override fun run(indicator: ProgressIndicator) {
        val modulePath = CirrusModuleManager.modulePath(module)
        val git = Git.getInstance()

        val moduleParentDir = VfsUtil.createDirectories(modulePath.parent.toString())
        moduleParentDir.findChild(modulePath.name)?.also {
          ApplicationManager.getApplication().runWriteAction { it.delete(this@DownloadGitHubModuleQuickFix) }
        }

        val success = GitCheckoutProvider.doClone(
          project,
          git,
          modulePath.name, modulePath.parent.toString(),
          "git@github.com:${module.org}/${module.repo}.git"
        )
        if (!success) {
          return
        }

        val moduleDir = VirtualFileManager.getInstance().refreshAndFindFileByNioPath(modulePath) ?: return

        if (module.revision != null && module.revision != "main") {
          val handler = GitLineHandler(project, moduleDir, GitCommand.CHECKOUT)
          handler.setSilent(false)
          handler.setStdoutSuppressed(false)
          handler.addParameters("-b", module.revision ?: "main")
          handler.endOptions()
          git.runCommand(handler).throwOnError()
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
