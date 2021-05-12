package org.cirruslabs.intellij.starlark

import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.jetbrains.python.PythonParserDefinition
import com.jetbrains.python.psi.impl.PyFileImpl

class StarlarkParserDefinition : PythonParserDefinition() {
  companion object {
    val FILE = IFileElementType(StarlarkLanguage.INSTANCE)
  }

  override fun getFileNodeType(): IFileElementType {
    return FILE
  }

  override fun createFile(viewProvider: FileViewProvider): PsiFile {
    return PyFileImpl(viewProvider, StarlarkLanguage.INSTANCE)
  }
}
