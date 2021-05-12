package org.cirruslabs.intellij.starlark.sdk

import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl
import com.intellij.openapi.roots.OrderRootType
import org.cirruslabs.intellij.starlark.StarlarkBundle
import org.cirruslabs.intellij.starlark.modules.CirrusModuleManager

object StarlarkSdk : ProjectJdkImpl(
  StarlarkBundle.getMessage("starlark.name"),
  StarlarkSdkType.getInstance(),
  CirrusModuleManager.CIRRUS_MODULE_HOME?.path,
  "latest"
) {
  init {
    CirrusModuleManager.CIRRUS_MODULE_HOME?.let {
      addRoot(it, OrderRootType.CLASSES)
      addRoot(it, OrderRootType.SOURCES)
    }
  }
}
