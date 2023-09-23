//[mpi18n](../../../index.md)/[net.sergeych.i18n](../index.md)/[I18n](index.md)/[addStrings](add-strings.md)

# addStrings

[common]\
fun [addStrings](add-strings.md)(langCodes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, strings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt;)

Add string translations for the specified language codes. Note that the language codes should already set up with [setup](setup.md).

The [strings](add-strings.md) parameter is an array of [langCodes](add-strings.md).size + 1 columns where columns are:

| Column # | Data |
|---|---|
| 0 | `stringId` or null |
| 1 | translation for language [langCodes](add-strings.md) at 0 |
| 2 | translation for language [langCodes](add-strings.md) at 1, etc.] |

See [addString](add-string.md) for explanation on `stringId`

#### Parameters

common

| | |
|---|---|
| langCodes | languages in which strings are |
| strings | array of [langCodes](add-strings.md).size + 1 columns as described above. |

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
   //sampleEnd
}
```
