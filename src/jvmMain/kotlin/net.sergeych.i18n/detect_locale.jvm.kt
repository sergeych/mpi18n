package net.sergeych.i18n

import net.sergeych.tools.i18n.I18n
import java.util.*

actual fun detectLocale(): I18n.Locale {
    return I18n.Locale(Locale.getDefault().country.lowercase())
}