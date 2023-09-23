//[mpi18n](../../../index.md)/[net.sergeych.i18n](../index.md)/[I18n](index.md)/[translate](translate.md)

# translate

[common]\
fun [translate](translate.md)(locale: [I18n.Locale](-locale/index.md), format: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Translate the string using specified locale, current fallback locale and registered strings table, then, if any [args](translate.md) are presented, transform it with [sprintf](https://github.com/sergeych/mp_stools#sprintf-syntax-summary). The module tries it best to find a translation for a string, it searches for the translations in the following order:

- 
   using [format](translate.md) as string ID (language-independent ID)
- 
   looking for a [format](translate.md) as a content string in some language

Then it tries to find translation for the specified [locale](translate.md), and then in [I18n.fallbackLocale](fallback-locale.md).

If still nothing it found, it will use the source value of the [format](translate.md).

See [addString](add-string.md) and [addStrings](add-strings.md) for registering translations and [Multistring](../-multistring/index.md) to use translations packed in the source string.

#### Parameters

common

| | |
|---|---|
| locale | desired locale to translate to. |
| format | string to translate, possible `sptintf` format (See above) |
| args | if present, will be used to format the *translated result* string using `sprintf`     rules (see the link above) |
