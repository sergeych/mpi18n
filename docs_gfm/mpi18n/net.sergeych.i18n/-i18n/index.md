//[mpi18n](../../../index.md)/[net.sergeych.i18n](../index.md)/[I18n](index.md)

# I18n

[common]\
object [I18n](index.md)

Translations engine, capable of:

- 
   Registering new language and translations on the fly: [addStrings](add-strings.md), [addString](add-string.md)
- 
   Translate `CharSequences` and format them: [I18n.translate](translate.md), [t](../t.md), [tf](../tf.md), [translate](translate.md), etc.
- 
   Retrieve proper translations packed in the data string itself, string with human-optimized forat, see [Multistring](../-multistring/index.md), [ms](../ms.md).

#### Samples

```kotlin
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.sergeych.sprintf.sprintf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
fun main() { 
   //sampleStart 
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
   //sampleEnd
}
```

## Types

| Name | Summary |
|---|---|
| [Locale](-locale/index.md) | [common]<br>class [Locale](-locale/index.md)(val code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Internal locale index, hashable. Optimizes access to localization tables by caching unique per-locale index of it. Attention, primary locale selector is **language**, not country code. |

## Properties

| Name | Summary |
|---|---|
| [defaultLocale](default-locale.md) | [common]<br>var [defaultLocale](default-locale.md): [I18n.Locale](-locale/index.md)<br>The default locale is used in functions and methods as the default value. It is detected on each platform from the system locale set at the time if module initialization. You can change it any time, so it will be used program-wide |
| [fallbackLocale](fallback-locale.md) | [common]<br>var [fallbackLocale](fallback-locale.md): [I18n.Locale](-locale/index.md)<br>The fallback locale is used when some translated item can't be found in the desired locale. By default, it is set to system default locale detected. |
| [languages](languages.md) | [common]<br>var [languages](languages.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addString](add-string.md) | [common]<br>fun [addString](add-string.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, vararg variants: [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)<br>Add string translation to table. |
| [addStrings](add-strings.md) | [common]<br>fun [addStrings](add-strings.md)(langCodes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, strings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt;)<br>Add string translations for the specified language codes. Note that the language codes should already set up with [setup](setup.md). |
| [setup](setup.md) | [common]<br>fun [setup](setup.md)(langCodes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, fallbackLangCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), strings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt; = listOf())<br>Setting up the locales. We recommend doing it early but only in the main program, better in `main` function, but not in the library module. |
| [translate](translate.md) | [common]<br>fun [translate](translate.md)(locale: [I18n.Locale](-locale/index.md), format: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Translate the string using specified locale, current fallback locale and registered strings table, then, if any [args](translate.md) are presented, transform it with [sprintf](https://github.com/sergeych/mp_stools#sprintf-syntax-summary). The module tries it best to find a translation for a string, it searches for the translations in the following order: |
