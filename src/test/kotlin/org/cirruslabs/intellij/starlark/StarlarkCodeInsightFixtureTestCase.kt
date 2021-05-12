package org.cirruslabs.intellij.starlark

import com.intellij.testFramework.LightProjectDescriptor
import com.intellij.testFramework.fixtures.BasePlatformTestCase

abstract class StarlarkCodeInsightFixtureTestCase : BasePlatformTestCase() {
  override fun getProjectDescriptor(): LightProjectDescriptor {
    return StarlarkTestProjectDescriptor
  }
}
