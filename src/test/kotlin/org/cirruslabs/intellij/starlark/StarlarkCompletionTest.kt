package org.cirruslabs.intellij.starlark

import com.intellij.codeInsight.completion.CompletionType


class StarlarkCompletionTest : StarlarkCodeInsightFixtureTestCase() {
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

  fun testLoadCirrusFS() {
    assertExpectedLookups("load_cirrus_fs.star", "exists")
  }

  fun testLoadCirrusJson() {
    assertExpectedLookups("load_cirrus_json.star", "json", "json_alias")
  }

  fun testOnBuildFailedHookPayload() {
    assertExpectedLookups("on_build_failed_hook_payload.star", "repository", "build", "task")
  }
}
