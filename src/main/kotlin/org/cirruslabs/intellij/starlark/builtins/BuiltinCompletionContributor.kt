package org.cirruslabs.intellij.starlark.builtins

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiManager
import com.intellij.util.ProcessingContext
import com.jetbrains.python.codeInsight.completion.PyKeywordCompletionContributor
import com.jetbrains.python.psi.PyFile
import org.cirruslabs.intellij.starlark.StarlarkLanguage


class BuiltinCompletionContributor : CompletionContributor() {
  init {
    val starlarkElement = PlatformPatterns.psiElement()
    extend(CompletionType.BASIC, starlarkElement.and(PyKeywordCompletionContributor.FIRST_ON_LINE),
      object : CompletionProvider<CompletionParameters>() {
        override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
          val element = parameters.position
          if (element.containingFile.language !is StarlarkLanguage) {
            return
          }
          val psiManager = PsiManager.getInstance(element.project)
          listOfNotNull(
            psiManager.findFile(BuitlinSetContributor.PYTHON_BUILTINS) as? PyFile,
            psiManager.findFile(BuitlinSetContributor.STARLARK_BUILTINS) as? PyFile,
          ).forEach { file ->
            file.topLevelClasses.forEach {
              result.addElement(LookupElementBuilder.create(it))
            }
            file.topLevelFunctions.forEach {
              result.addElement(LookupElementBuilder.create(it))
            }
          }
        }
      })
  }
}
