//[mpi18n](../../../../index.md)/[net.sergeych.i18n](../../index.md)/[Multistring](../index.md)/[Companion](index.md)

# Companion

[common]\
object [Companion](index.md)

## Properties

| Name | Summary |
|---|---|
| [reDeinition](re-deinition.md) | [common]<br>val [reDeinition](re-deinition.md): [Regex](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/-regex/index.html) |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [common]<br>operator fun [invoke](invoke.md)(definition: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Multistring](../index.md)<br>Same as [parse](parse.md), syntax sugar to create multistring from an encoded string. See [parse](parse.md) on format. |
| [parse](parse.md) | [common]<br>fun [parse](parse.md)(definition: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ignoreErrors: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): [Multistring](../index.md)<br>Parse encoded human-friendly source like: |
