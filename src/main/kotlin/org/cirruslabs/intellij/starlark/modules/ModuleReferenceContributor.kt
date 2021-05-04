package org.cirruslabs.intellij.starlark.modules

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet
import com.intellij.util.ProcessingContext
import com.jetbrains.python.psi.PyArgumentList
import com.jetbrains.python.psi.PyStringLiteralExpression

class ModuleReferenceContributor : PsiReferenceContributor() {
  override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
    registrar.registerReferenceProvider(
      PlatformPatterns.psiElement().and(PlatformPatterns.instanceOf(PyStringLiteralExpression::class.java)),
      object : PsiReferenceProvider() {
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
          val argumentList = element.parent as? PyArgumentList ?: return PsiReference.EMPTY_ARRAY
          if (argumentList.arguments.firstOrNull() != element) return PsiReference.EMPTY_ARRAY
          if (element is PyStringLiteralExpression && argumentList.callExpression?.callee?.name == "load") {
            if (element.stringValue.startsWith("github.com") || element.stringValue == "cirrus") {
              return arrayOf(ModuleReference(element))
            }
            return FileReferenceSet.createSet(element, false, false, false).allReferences
              .toList().toTypedArray()
          }
          return PsiReference.EMPTY_ARRAY
        }
      }
    )
  }
}
