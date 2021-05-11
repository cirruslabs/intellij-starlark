package org.cirruslabs.intellij.starlark.modules

import com.intellij.ide.caches.CachesInvalidator
import com.intellij.openapi.util.io.FileUtil

class CirrusModulesCacheInvalidator : CachesInvalidator() {
  override fun invalidateCaches() {
    FileUtil.delete(CirrusModuleManager.globalCacheLocation)
  }
}
