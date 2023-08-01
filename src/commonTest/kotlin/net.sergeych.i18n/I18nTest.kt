package net.sergeych.tools.i18n

import kotlin.test.*

class I18nTest {

    init {
        I18n.setup(listOf("en", "ru"), "ru")
    }

    @Test
    fun translateTest() {
        I18n.addStrings(
            listOf("en", "ru"),
            listOf(
                null, "foo", "фу",
                "idbar", "bar", "бар",
                null, "format %s", "формат %s"
            )
        )

        I18n.addString(null, "ru" to "русский", "en" to "not russian")
        I18n.addString("franc", "ru" to "французский", "en" to "french")

        I18n.defaultLocale = I18n.Locale("ru")
        I18n.fallbackLocale = I18n.Locale("en")

        println(I18n.defaultLocale)
        println(I18n.fallbackLocale)

        assertEquals("фу", "foo".t)
        assertEquals("фу", "фу".t)
        assertEquals("бар", "idbar".t)
        assertEquals("бар", "bar".t)
        assertEquals("бар", "бар".t)

        assertEquals("русский", "not russian".t)
        assertEquals("французский", "franc".t)

        assertEquals("формат привет", "format %s".tf("привет"))

    }
}