package org.cirruslabs.intellij.starlark.sdk

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl
import com.intellij.openapi.roots.OrderRootType
import org.cirruslabs.intellij.starlark.StarlarkBundle
import org.cirruslabs.intellij.starlark.modules.CirrusModuleManager

class StarlarkSdk : ProjectJdkImpl(
  StarlarkBundle.getMessage("starlark.name"),
  StarlarkSdkType.getInstance(),
  CirrusModuleManager.CIRRUS_MODULE_HOME?.path,
  "latest"
)
