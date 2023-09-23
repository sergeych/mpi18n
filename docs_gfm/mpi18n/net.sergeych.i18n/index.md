//[mpi18n](../../index.md)/[net.sergeych.i18n](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [I18n](-i18n/index.md) | [common]<br>object [I18n](-i18n/index.md)<br>Translations engine, capable of: |
| [Multistring](-multistring/index.md) | [common]<br>class [Multistring](-multistring/index.md)(val values: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)<br>Simple way to store translated things in **the data** when you need it. |
| [MultistringSerializer](-multistring-serializer/index.md) | [common]<br>object [MultistringSerializer](-multistring-serializer/index.md) : KSerializer&lt;[Multistring](-multistring/index.md)&gt; <br>Multistring is serialized as human-readable/editable string for convenience. It also most often denser format than a Map. |

## Properties

| Name | Summary |
|---|---|
| [ms](ms.md) | [common]<br>val [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[ms](ms.md): [Multistring](-multistring/index.md)<br>Transform the String object into Multistring using [Multistring.parse](-multistring/-companion/parse.md) format. |
| [t](t.md) | [common]<br>val [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html).[t](t.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Translate the sequence using a string tables using [I18n.translate](-i18n/translate.md). If you need to *select translation packed in this string*, use [ms](ms.md) instead, otherwise see [Multistring](-multistring/index.md). |

## Functions

| Name | Summary |
|---|---|
| [tf](tf.md) | [common]<br>fun [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html).[tf](tf.md)(vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Translate this sequence using [I18n.translate](-i18n/translate.md) and default locale, then use it as a format string for `String.sptintf` if any [args](tf.md) is present; see [sprintf from included mp_stools](https://github.com/sergeych/mp_stools#sprintf-syntax-summary) package. |
| [translate](translate.md) | [common]<br>fun [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html).[translate](translate.md)(langCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Translate this sequence as string using a long code (not recommended), the same as [I18n.translate](-i18n/translate.md).<br>[common]<br>fun [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html).[translate](translate.md)(locale: [I18n.Locale](-i18n/-locale/index.md) = I18n.defaultLocale, vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Translate this sequence as string with a specified locale, the same as [I18n.translate](-i18n/translate.md). |
