package org.cirruslabs.intellij.starlark.sdk

import com.intellij.facet.FacetType
import com.intellij.framework.detection.FacetBasedFrameworkDetector
import com.intellij.framework.detection.FileContentPattern
import com.intellij.openapi.fileTypes.FileType
import com.intellij.patterns.ElementPattern
import com.intellij.util.indexing.FileContent
import org.cirruslabs.intellij.starlark.StarlarkFileType
import org.cirruslabs.intellij.starlark.facet.StarlarkFacet
import org.cirruslabs.intellij.starlark.facet.StarlarkFacetType

class StarlarkDetector : FacetBasedFrameworkDetector<StarlarkFacet, StarlarkFacetType.StarlarkFacetConfiguration>("starlark") {
  override fun getFileType(): FileType = StarlarkFileType.INSTANCE

  override fun createSuitableFilePattern(): ElementPattern<FileContent> =
    FileContentPattern.fileContent()

  override fun getFacetType(): FacetType<StarlarkFacet, StarlarkFacetType.StarlarkFacetConfiguration> =
    StarlarkFacetType.instance
}
