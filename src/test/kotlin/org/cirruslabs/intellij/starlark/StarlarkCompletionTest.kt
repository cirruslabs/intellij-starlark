package org.cirruslabs.intellij.starlark

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase


class StarlarkCompletionTest : LightJavaCodeInsightFixtureTestCase() {
  override fun getTestDataPath(): String {
    return "src/test/testData/completion"
  }

  private fun assertExpectedLookups(filePath: String, vararg expected: String) {
    myFixture.configureByFiles(filePath)
    myFixture.complete(CompletionType.BASIC, 1)
    val lookupElement = myFixture.lookupElementStrings ?: emptyList()
    assertContainsElements(lookupElement, *expected)
  }

  fun testBuiltin() {
    assertExpectedLookups("builtin.star", "print", "load")
  }

  fun testLoadCirrusEnv() {
    assertExpectedLookups("load_cirrus_env.star", "get")
  }

  fun testLoadCirrusFS() {
    assertExpectedLookups("load_cirrus_fs.star", "exists")
  }

  fun testLoadCirrusJson() {
    assertExpectedLookups("load_cirrus_json.star", "json", "json_alias")
  }
}
