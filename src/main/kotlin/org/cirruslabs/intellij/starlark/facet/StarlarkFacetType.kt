package org.cirruslabs.intellij.starlark.facet

import com.intellij.facet.Facet
import com.intellij.facet.FacetConfiguration
import com.intellij.facet.FacetType
import com.intellij.facet.ui.FacetEditorContext
import com.intellij.facet.ui.FacetEditorTab
import com.intellij.facet.ui.FacetValidatorsManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.projectRoots.Sdk
import com.jetbrains.python.facet.PythonFacetSettings
import org.cirruslabs.intellij.starlark.sdk.StarlarkSdk
import javax.swing.Icon

class StarlarkFacetType : FacetType<StarlarkFacet, StarlarkFacetType.StarlarkFacetConfiguration>(
  StarlarkFacet.ID, ID, "Starlark"
) {
  companion object {
    private val ID = "Starlark"

    val instance: StarlarkFacetType
      get() = findInstance(StarlarkFacetType::class.java)
  }

  override fun createDefaultConfiguration(): StarlarkFacetConfiguration {
    return StarlarkFacetConfiguration()
  }

  override fun createFacet(
    module: Module,
    name: String,
    configuration: StarlarkFacetConfiguration,
    underlyingFacet: Facet<*>?
  ): StarlarkFacet {
    return StarlarkFacet(this, module, name, configuration, underlyingFacet)
  }

  override fun isSuitableModuleType(moduleType: ModuleType<*>): Boolean = true

  override fun getIcon(): Icon {
    return AllIcons.Ide.Rating
  }

  class StarlarkFacetConfiguration : PythonFacetSettings(), FacetConfiguration {
    override fun getSdk(): Sdk {
      return StarlarkSdk()
    }

    override fun createEditorTabs(
      editorContext: FacetEditorContext,
      validatorsManager: FacetValidatorsManager
    ): Array<FacetEditorTab> {
      return arrayOf()
    }
  }
}
