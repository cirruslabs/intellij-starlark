package org.cirruslabs.intellij.starlark.modules

import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider
import com.intellij.openapi.util.TextRange
import com.intellij.psi.ElementManipulators
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.jetbrains.python.psi.PyStringLiteralExpression
import org.cirruslabs.intellij.starlark.StarlarkBundle

class ModuleReference(private val exp: PyStringLiteralExpression) : PsiReference, EmptyResolveMessageProvider {
  val module: String
    get() = exp.stringValue

  override fun getElement(): PsiElement = exp

  override fun isSoft(): Boolean = false

  override fun getUnresolvedMessagePattern(): String =
    StarlarkBundle.getMessage("starlark.unresolved.module.reference")

  override fun getRangeInElement(): TextRange = ElementManipulators.getValueTextRange(exp)

  override fun getCanonicalText(): String = exp.stringValue

  override fun resolve(): PsiElement? {
    return CirrusModuleManager.resolveModuleFile(exp.containingFile, exp.stringValue)
  }

  override fun handleElementRename(newElementName: String): PsiElement = exp

  override fun bindToElement(element: PsiElement): PsiElement = exp

  override fun isReferenceTo(element: PsiElement): Boolean =
    exp.manager.areElementsEquivalent(resolve(), exp)
}
