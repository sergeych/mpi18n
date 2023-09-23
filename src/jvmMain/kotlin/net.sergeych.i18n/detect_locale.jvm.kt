package net.sergeych.i18n

import java.util.*

internal actual fun detectLocale(): I18n.Locale {
    return I18n.Locale(Locale.getDefault().language.lowercase())
}