package org.cirruslabs.intellij.starlark.modules

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.psi.PsiReference
import com.intellij.util.Consumer
import com.jetbrains.python.inspections.PyUnresolvedReferenceQuickFixProvider

class UnresolvedModuleReferenceQuickFixProvider : PyUnresolvedReferenceQuickFixProvider {
  override fun registerQuickFixes(ref: PsiReference, existing: MutableList<LocalQuickFix>) {
    if (ref is ModuleReference && ref.module.startsWith("github.com/")) {
      try {
        existing.add(DownloadGitHubModuleQuickFix(ref.module))
      } catch (_: IllegalStateException) {
      }
    }
  }
}
