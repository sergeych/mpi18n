//[mpi18n](../../../../index.md)/[net.sergeych.i18n](../../index.md)/[I18n](../index.md)/[Locale](index.md)

# Locale

class [Locale](index.md)(val code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Internal locale index, hashable. Optimizes access to localization tables by caching unique per-locale index of it. Attention, primary locale selector is **language**, not country code.

Important: when you create a locale, it's ok, but it is also registered in the module adding new language if needed. We recommend reusing `Locale` instances, though the impact of it is minimal.

#### Parameters

common

| | |
|---|---|
| code | ISO language code (two letters where there is the choice) |

## Constructors

| | |
|---|---|
| [Locale](-locale.md) | [common]<br>constructor(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [code](code.md) | [common]<br>val [code](code.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [index](--index--.md) | [common]<br>val [index](--index--.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [common]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [common]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [t](t.md) | [common]<br>fun [t](t.md)(str: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Translate a string using locale, same as [I18n.translate](../translate.md). |
| [toString](to-string.md) | [common]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [translate](translate.md) | [common]<br>fun [translate](translate.md)(what: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Translate a string using locale, same as [I18n.translate](../translate.md). |
