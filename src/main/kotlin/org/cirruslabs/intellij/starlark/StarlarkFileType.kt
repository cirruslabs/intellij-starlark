package org.cirruslabs.intellij.starlark

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class StarlarkFileType private constructor() : LanguageFileType(StarlarkLanguage.INSTANCE) {
  companion object {
    val INSTANCE = StarlarkFileType()
  }

  override fun getName(): String =
    StarlarkBundle.getMessage("starlark.name")

  override fun getDescription(): String =
    StarlarkBundle.getMessage("starlark.description")

  override fun getDefaultExtension(): String = "star"

  override fun getIcon(): Icon = AllIcons.Ide.Rating
}
