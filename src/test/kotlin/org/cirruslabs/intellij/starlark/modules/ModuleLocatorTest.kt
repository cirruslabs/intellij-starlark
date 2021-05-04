package org.cirruslabs.intellij.starlark.modules

import org.junit.Test
import kotlin.test.assertEquals


class ModuleLocatorTest {
  @Test
  fun parse() {
    assertEquals(
      ModuleLocator("some-org", "some-repo"),
      ModuleLocator.parse("github.com/some-org/some-repo")
    )
    assertEquals(
      ModuleLocator("some-org", "some-repo", "lib.star"),
      ModuleLocator.parse("github.com/some-org/some-repo/lib.star")
    )
    assertEquals(
      ModuleLocator("some-org", "some-repo", "lib.star", "dev"),
      ModuleLocator.parse("github.com/some-org/some-repo/lib.star@dev")
    )
  }
}
