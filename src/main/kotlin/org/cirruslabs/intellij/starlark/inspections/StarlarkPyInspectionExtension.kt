package org.cirruslabs.intellij.starlark.inspections

import com.jetbrains.python.inspections.PyInspectionExtension
import com.jetbrains.python.psi.PyFile
import org.cirruslabs.intellij.starlark.StarlarkLanguage

class StarlarkPyInspectionExtension : PyInspectionExtension() {
  override fun ignoreInterpreterWarnings(file: PyFile): Boolean {
    return file.language is StarlarkLanguage
  }
}
