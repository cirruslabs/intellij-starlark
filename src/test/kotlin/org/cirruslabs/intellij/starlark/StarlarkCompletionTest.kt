package org.cirruslabs.intellij.starlark

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase


class StarlarkCompletionTest : LightJavaCodeInsightFixtureTestCase() {
  override fun getTestDataPath(): String {
    return "src/test/testData/completion"
  }

  fun testCompletion() {
    myFixture.configureByFiles("builtin.star")
    myFixture.complete(CompletionType.BASIC, 1)
    val lookupElement = myFixture.lookupElementStrings ?: emptyList()
    assertContainsElements(lookupElement, "print", "load")
  }
}
