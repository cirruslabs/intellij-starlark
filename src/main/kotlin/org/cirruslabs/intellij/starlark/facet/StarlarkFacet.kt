package org.cirruslabs.intellij.starlark.facet

import com.intellij.facet.Facet
import com.intellij.facet.FacetType
import com.intellij.facet.FacetTypeId
import com.intellij.openapi.module.Module

class StarlarkFacet(
  facetType: FacetType<*, *>,
  module: Module,
  name: String,
  configuration: StarlarkFacetType.StarlarkFacetConfiguration,
  underlyingFacet: Facet<*>? = null
) : Facet<StarlarkFacetType.StarlarkFacetConfiguration>(facetType, module, name, configuration, underlyingFacet) {
  companion object {
    val ID = FacetTypeId<StarlarkFacet>("starlark")
  }

  override fun initFacet() {
  }
}
