package net.sergeych.i18n

import kotlinx.browser.window
import net.sergeych.tools.i18n.I18n

actual fun detectLocale(): I18n.Locale {
    fun set(code: String): I18n.Locale =
        I18n.Locale(code.split('_')[0])

    val n = window.navigator
    if (n.language.isNotBlank()) return set(n.language)
    return set(n.languages[0])
}