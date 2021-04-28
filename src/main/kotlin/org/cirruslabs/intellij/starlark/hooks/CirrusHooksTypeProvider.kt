package org.cirruslabs.intellij.starlark.hooks

import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiManager
import com.jetbrains.python.psi.PyFile
import com.jetbrains.python.psi.PyFunction
import com.jetbrains.python.psi.PyNamedParameter
import com.jetbrains.python.psi.types.PyClassTypeImpl
import com.jetbrains.python.psi.types.PyType
import com.jetbrains.python.psi.types.PyTypeProviderBase
import com.jetbrains.python.psi.types.TypeEvalContext
import org.cirruslabs.intellij.starlark.modules.CirrusModuleSetContributor

class CirrusHooksTypeProvider : PyTypeProviderBase() {
  override fun getParameterType(param: PyNamedParameter, func: PyFunction, context: TypeEvalContext): Ref<PyType>? {
    val taskHook = func.name?.startsWith("on_task_") ?: false
    val buildHook = func.name?.startsWith("on_build_") ?: false
    if (!taskHook && !buildHook) return null
    val cirrusModuleFile = PsiManager.getInstance(param.project).findFile(
      CirrusModuleSetContributor.CIRRUS_MODULE ?: return null
    ) as? PyFile ?: return null
    val ctxClass = cirrusModuleFile.findTopLevelClass("CirrusHookContext")
    return ctxClass?.let { Ref.create(PyClassTypeImpl(it, true)) }
  }
}
