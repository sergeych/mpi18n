package net.sergeych.i18n

//import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import net.sergeych.tools.i18n.I18n
import platform.posix.getenv

//@OptIn(ExperimentalForeignApi::class)
actual fun detectLocale(): I18n.Locale {
    getenv("LANG")?.toKString()?.let {
        val parts = it.split('_')
        if( parts.size >= 2 || parts[0].length == 2)
            return I18n.Locale(parts[0])
    }
    return I18n.Locale("en")
}