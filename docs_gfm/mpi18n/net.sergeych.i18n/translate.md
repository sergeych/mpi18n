//[mpi18n](../../index.md)/[net.sergeych.i18n](index.md)/[translate](translate.md)

# translate

[common]\
fun [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html).[translate](translate.md)(locale: [I18n.Locale](-i18n/-locale/index.md) = I18n.defaultLocale, vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Translate this sequence as string with a specified locale, the same as [I18n.translate](-i18n/translate.md).

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

[common]\
fun [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html).[translate](translate.md)(langCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Translate this sequence as string using a long code (not recommended), the same as [I18n.translate](-i18n/translate.md).

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
