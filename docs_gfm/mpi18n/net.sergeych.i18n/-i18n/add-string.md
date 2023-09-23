//[mpi18n](../../../index.md)/[net.sergeych.i18n](../index.md)/[I18n](index.md)/[addString](add-string.md)

# addString

[common]\
fun [addString](add-string.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, vararg variants: [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)

Add string translation to table.

**Note for library authors.** It is safe to add translations in your languages, but we recommend to use prefixed [id](add-string.md) like &quot;mylibrary.nothingFound&quot; to avoid clashes. Also if you public the list of your ids, users could add translations in their applications on the fly.

#### Parameters

common

| | |
|---|---|
| id | the id of the translation item, will be used first when translating.     If not set, the first matching translation will be used that is not always good.     We recommend to always ise ids. |
| variants | translations in the format `"langCode" to "translation"` |

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
    listOf("en", "es", "ru"),
    listOf("wup", "wazzup?", "¿qué pasa?", "какого???")
)
assertEquals("какого???","wup".t)
assertEquals("wazzup?","wup".translate("en"))
assertEquals("¿qué pasa?",I18n.Locale("es").t("wup")) 
   //sampleEnd
}
```
