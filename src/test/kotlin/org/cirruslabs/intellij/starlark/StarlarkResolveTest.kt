package org.cirruslabs.intellij.starlark


class StarlarkResolveTest : StarlarkCodeInsightFixtureTestCase() {
  override fun getTestDataPath(): String {
    return "src/test/testData/resolve"
  }

  private fun assertReferenceResolves(filePath: String) {
    myFixture.configureByFiles(filePath)
    val reference = myFixture.file.findReferenceAt(myFixture.caretOffset)
    assertNotNull("No reference for element '${myFixture.file.findElementAt(myFixture.caretOffset)?.text}'", reference)
    val resolvedElement = reference?.resolve()
    assertNotNull("Reference can't be resolved", resolvedElement)
  }

  fun testBuiltin() {
    assertReferenceResolves("builtin.star")
  }

  fun testLoadCirrusJson() {
    assertReferenceResolves("load_cirrus_json.star")
  }
}
