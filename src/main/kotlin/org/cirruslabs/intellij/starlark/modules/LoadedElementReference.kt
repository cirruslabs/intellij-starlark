package org.cirruslabs.intellij.starlark.modules

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.jetbrains.python.psi.PyStringLiteralExpression

class LoadedElementReference(
  private val module: String,
  private val literal: PyStringLiteralExpression
) : PsiReference {
  override fun getElement(): PsiElement = literal

  override fun isSoft(): Boolean = false

  override fun getRangeInElement(): TextRange = literal.stringValueTextRange

  override fun resolve(): PsiElement? =
    CirrusModuleManager.resolveModuleFile(element.containingFile, module)
      ?.findExportedName(literal.stringValue)

  override fun getCanonicalText(): String = literal.stringValue

  override fun handleElementRename(newElementName: String): PsiElement = literal

  override fun bindToElement(element: PsiElement): PsiElement = literal

  override fun isReferenceTo(element: PsiElement): Boolean =
    literal.manager.areElementsEquivalent(resolve(), literal)

  override fun getVariants(): Array<Any> {
    val moduleFile = CirrusModuleManager.resolveModuleFile(element.containingFile, module) ?: return emptyArray()
    return (moduleFile.topLevelAttributes.toList<PsiElement>() + moduleFile.topLevelFunctions.toList<PsiElement>())
      .toTypedArray()
  }
}
