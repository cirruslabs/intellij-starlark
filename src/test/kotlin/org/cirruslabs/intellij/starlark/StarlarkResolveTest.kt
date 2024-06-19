package org.cirruslabs.intellij.starlark


class StarlarkResolveTest : StarlarkCodeInsightFixtureTestCase() {
  override fun getTestDataPath(): String {
    return "src/test/testData/resolve"
  }

  private fun assertReferenceResolves(vararg filePaths: String) {
    myFixture.configureByFiles(*filePaths)
    val reference = myFixture.file.findReferenceAt(myFixture.caretOffset)
    assertNotNull("No reference for element '${myFixture.file.findElementAt(myFixture.caretOffset)?.text}'", reference)
    val resolvedElement = reference?.resolve()
    assertNotNull("Reference can't be resolved", resolvedElement)
  }

  fun _testBuiltinPrint() {
    assertReferenceResolves("builtin_print.star")
  }

  fun testBuiltinFail() {
    assertReferenceResolves("builtin_fail.star")
  }

  fun testLoadCirrusJson() {
    assertReferenceResolves("load_cirrus_json.star")
  }

  fun testLoadLocalFile() {
    assertReferenceResolves("load_cirrus_json.star", "foo/foo.star")
  }
}
