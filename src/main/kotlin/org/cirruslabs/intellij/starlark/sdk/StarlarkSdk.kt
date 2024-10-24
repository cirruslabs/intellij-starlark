package org.cirruslabs.intellij.starlark.sdk

import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl
import com.jetbrains.python.sdk.PythonSdkType
import org.cirruslabs.intellij.starlark.StarlarkBundle
import org.cirruslabs.intellij.starlark.modules.CirrusModuleManager

class StarlarkSdk : ProjectJdkImpl(
  StarlarkBundle.getMessage("starlark.name"),
  PythonSdkType.getInstance(),
  CirrusModuleManager.CIRRUS_MODULE_HOME?.path,
  "latest"
)
