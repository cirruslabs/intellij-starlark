package org.cirruslabs.intellij.starlark.modules

import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ResourceUtil
import com.intellij.util.indexing.IndexableSetContributor


class CirrusModuleSetContributor : IndexableSetContributor() {
  companion object {
    val CIRRUS_MODULE by lazy {
      val url = ResourceUtil.getResource(javaClass.classLoader, "modules", "cirrus.star")
      VfsUtil.findFileByURL(url)
    }
  }

  override fun getAdditionalRootsToIndex(): Set<VirtualFile> {
    return setOfNotNull(CIRRUS_MODULE?.parent)
  }
}
