package org.cirruslabs.intellij.starlark.modules

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project

class DownloadGitHubModuleQuickFix(val module: ModuleLocator) : LocalQuickFix {
  constructor(module: String): this(ModuleLocator.parse(module))

  override fun getFamilyName(): String =
    "Download ${module.org}/${module.repo} module"

  override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
  }
}
