package org.cirruslabs.intellij.starlark.modules

import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.jetbrains.python.psi.*
import com.jetbrains.python.psi.resolve.PyReferenceResolveProvider
import com.jetbrains.python.psi.resolve.RatedResolveResult
import com.jetbrains.python.psi.types.TypeEvalContext
import org.cirruslabs.intellij.starlark.StarlarkLanguage

class LoadedReferenceResolveProvider : PyReferenceResolveProvider {
  override fun resolveName(expression: PyQualifiedExpression, context: TypeEvalContext): List<RatedResolveResult> {
    if (expression.containingFile.language !is StarlarkLanguage) {
      return emptyList()
    }
    val (module, id) = findModuleAndActualIdentifier(expression.containingFile, expression.name ?: return emptyList())
      ?: return emptyList()
    val moduleFile = resolveModuleFile(expression.containingFile, module)
      ?: return emptyList()
    return listOfNotNull(
      moduleFile.findExportedName(id)?.let {
        RatedResolveResult(RatedResolveResult.RATE_NORMAL, it)
      }
    )
  }

  private fun resolveModuleFile(originalFile: PsiFile, module: String): PyFile? {
    if (module == "cirrus") {
      return PsiManager.getInstance(originalFile.project).findFile(
        CirrusModuleSetContributor.CIRRUS_MODULE ?: return null
      ) as? PyFile
    }
    return null
  }

  private fun findModuleAndActualIdentifier(file: PsiFile, name: String): Pair<String, String>? {
    val loadStatements = file.children
      .mapNotNull { it as? PyExpressionStatement }
      // call expression is wrapped in a statement
      .mapNotNull { it.children.firstOrNull() as? PyCallExpression }
      .filter { it.callee?.name == "load" }
    for (statement in loadStatements) {
      val module = statement.arguments.firstOrNull() as? PyStringLiteralExpression
        ?: continue
      statement.arguments.drop(1).forEach { argument ->
        when (argument) {
          is PyStringLiteralExpression ->
            if (argument.stringValue == name) {
              return module.stringValue to name
            }
          is PyKeywordArgument -> {
            val argumentValue = argument.valueExpression as? PyStringLiteralExpression
            if (argument.keyword == name && argumentValue != null) {
              return module.stringValue to argumentValue.stringValue
            }
          }
        }
      }
    }
    return null
  }
}
