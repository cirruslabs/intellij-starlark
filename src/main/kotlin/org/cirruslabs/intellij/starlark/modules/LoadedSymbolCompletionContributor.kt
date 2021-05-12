package org.cirruslabs.intellij.starlark.modules

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.jetbrains.python.codeInsight.completion.PyKeywordCompletionContributor
import com.jetbrains.python.psi.PyCallExpression
import com.jetbrains.python.psi.PyExpressionStatement
import com.jetbrains.python.psi.PyKeywordArgument
import com.jetbrains.python.psi.PyStringLiteralExpression
import org.cirruslabs.intellij.starlark.StarlarkLanguage


class LoadedSymbolCompletionContributor : CompletionContributor() {
  init {
    extend(CompletionType.BASIC, PlatformPatterns.psiElement().and(PyKeywordCompletionContributor.FIRST_ON_LINE),
      object : CompletionProvider<CompletionParameters>() {
        override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
          val element = parameters.position
          if (element.containingFile.language !is StarlarkLanguage) {
            return
          }
          val loadStatements = element.containingFile.children
            .mapNotNull { it as? PyExpressionStatement }
            // call expression is wrapped in a statement
            .mapNotNull { it.children.firstOrNull() as? PyCallExpression }
            .filter { it.callee?.name == "load" }
          loadStatements.forEach { statement ->
            statement.arguments.drop(1).forEach { argument ->
              when (argument) {
                is PyStringLiteralExpression ->
                  result.addElement(LookupElementBuilder.create(argument.stringValue))
                is PyKeywordArgument ->
                  argument.keyword?.let { result.addElement(LookupElementBuilder.create(it)) }
              }
            }
          }
        }
      })
  }
}
