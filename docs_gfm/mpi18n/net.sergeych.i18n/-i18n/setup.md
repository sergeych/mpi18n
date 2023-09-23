//[mpi18n](../../../index.md)/[net.sergeych.i18n](../index.md)/[I18n](index.md)/[setup](setup.md)

# setup

[common]\
fun [setup](setup.md)(langCodes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, fallbackLangCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), strings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt; = listOf())

Setting up the locales. We recommend doing it early but only in the main program, better in `main` function, but not in the library module.

The idea is, the main program initializes the locales and sets default and fallback, or leave it to the system defaults (as set in OS or browser).

Library modules instead should not initialize the library but add its own translations, and better publish their translation ids somewhere so library users could add their specific translations to their code. See [addString](add-string.md)'s note for library authors.

#### Parameters

common

| | |
|---|---|
| langCodes | list of language codes you mean to use |
| fallbackLangCode | fallback language code; should be listed in [langCodes](setup.md) |
| strings | optional list of translations, see [addStrings](add-strings.md) for format description. |
