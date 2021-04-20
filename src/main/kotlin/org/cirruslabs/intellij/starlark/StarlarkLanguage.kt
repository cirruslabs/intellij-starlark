package org.cirruslabs.intellij.starlark

import com.intellij.lang.Language
import com.jetbrains.python.PythonLanguage

class StarlarkLanguage : Language(PythonLanguage.INSTANCE, "Starlark") {
  companion object {
    val INSTANCE = StarlarkLanguage()
  }
}
