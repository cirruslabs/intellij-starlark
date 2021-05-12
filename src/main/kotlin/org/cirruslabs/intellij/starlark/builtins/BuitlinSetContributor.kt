package org.cirruslabs.intellij.starlark.builtins

import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ResourceUtil
import com.intellij.util.indexing.IndexableSetContributor


class BuitlinSetContributor : IndexableSetContributor() {
  companion object {
    val STARLARK_BUILTINS by lazy {
      val url = ResourceUtil.getResource(javaClass.classLoader, "builtins", "starlark_builtins.star")
      VfsUtil.findFileByURL(url)!!
    }
  }

  override fun getAdditionalRootsToIndex(): Set<VirtualFile> {
    return setOfNotNull(STARLARK_BUILTINS.parent)
  }
}
