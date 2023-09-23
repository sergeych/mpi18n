package net.sergeych.i18n

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.sergeych.sprintf.sprintf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class I18nTest {

    init {
        I18n.setup(listOf("en", "ru"), "ru")
    }

    @Test
    fun defaultLocale() {
        // locale should not be a country code but a lang code
        // we yet do not cope with countries, not now:
        assertNotEquals("us", I18n.defaultLocale.code)
    }

    @Test
    fun translationTest() {
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
    @Test
    fun translationTest2() {
        I18n.addStrings(
            listOf("en", "ru"),
            listOf(
                "grant_admin",
                "grant admin rights",
                "предоставить права администратора",
                //
                "set_max_storage=%s",
                "upgrade maximum storage capacity to %s",
                "увеличить размер хранилища до %s",
                //
                "increase_storage_by_%s",
                "add %s to the storage",
                "увеличить размер хранилища до %s",
                //
                "until_%s",
                " not exceeding than %s",
                " не превышая %s",
            )
        )

    }

    @Test
    fun addNewLangTest() {
        I18n.addStrings(
            listOf("en", "es", "ru"),
            listOf("wup", "wazzup?", "¿qué pasa?", "какого???")
        )
        assertEquals("какого???","wup".t)
        assertEquals("wazzup?","wup".translate("en"))
        assertEquals("¿qué pasa?",I18n.Locale("es").t("wup"))
    }


            @Test
    fun testMultistring() {
        // encoded multistring with escaped backslashes:
        val src = """\en Hello, %s! \fr Salut \\test\\ !"""

        // Most obvious way to create instance:
        val ms = Multistring.parse(src)

        // Now we can extract localized strings:
        assertEquals("Hello, %s!", ms["en"])
        assertEquals("""Salut \test\ !""", ms["fr"])

        // Assuming the fallback locale is "en" as by default, ti falls back too:
        assertEquals("Hello, %s!", ms["ee"])

        // Pseudo-constructor
        assertEquals("""Salut \test\ !""", Multistring(src)["fr"])

        // Most laconic: extension
        assertEquals("""Salut \test\ !""", src.ms["fr"])

        // kotlinx serialization uses a packed, human-friendly format too:
        assertEquals(""""[en]= Hello, %s! [fr]= Salut \\\\test\\\\ !"""", Json.encodeToString(ms))

        // format with such strings:
        assertEquals("Hello, Frank!",
            """\en Hello, %s! \fr Salut \\test\\ !"""
                .ms["en"]
                .sprintf("Frank"))
    }
}