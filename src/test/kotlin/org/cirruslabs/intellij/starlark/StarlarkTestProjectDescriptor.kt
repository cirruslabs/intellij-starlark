package org.cirruslabs.intellij.starlark

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.testFramework.LightProjectDescriptor
import org.cirruslabs.intellij.starlark.sdk.StarlarkSdk

object StarlarkTestProjectDescriptor : LightProjectDescriptor() {
  override fun getSdk(): Sdk = StarlarkSdk
}
