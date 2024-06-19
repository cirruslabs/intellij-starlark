package org.cirruslabs.intellij.starlark.sdk

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.projectRoots.*
import com.intellij.openapi.roots.OrderRootType
import com.jetbrains.python.sdk.PythonSdkType
import org.cirruslabs.intellij.starlark.StarlarkBundle
import org.cirruslabs.intellij.starlark.modules.CirrusModuleManager
import org.jdom.Element

class StarlarkSdkType : SdkType("Starlark") {
  companion object {
    fun getInstance(): PythonSdkType? {
      return findInstance(PythonSdkType::class.java)
    }
  }

  override fun getPresentableName(): String =
    StarlarkBundle.getMessage("starlark.name")

  override fun saveAdditionalData(additionalData: SdkAdditionalData, additional: Element) {
  }

  override fun suggestHomePath(): String? = null

  override fun isValidSdkHome(path: String): Boolean = true

  override fun suggestSdkName(currentSdkName: String?, sdkHome: String): String =
    StarlarkBundle.getMessage("starlark.name")

  override fun createAdditionalDataConfigurable(
    sdkModel: SdkModel,
    sdkModificator: SdkModificator
  ): AdditionalDataConfigurable? = null

  override fun setupSdkPaths(sdk: Sdk) {
    val modificator = sdk.sdkModificator
    CirrusModuleManager.CIRRUS_MODULE_HOME?.let {
      modificator.addRoot(it, OrderRootType.CLASSES)
      modificator.addRoot(it, OrderRootType.SOURCES)
    }
    modificator.commitChanges()
  }
}
