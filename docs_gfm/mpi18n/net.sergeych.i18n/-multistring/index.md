//[mpi18n](../../../index.md)/[net.sergeych.i18n](../index.md)/[Multistring](index.md)

# Multistring

[common]\
class [Multistring](index.md)(val values: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)

Simple way to store translated things in **the data** when you need it.

The source format is rather simple:

```kotlin
\en Hello! \fr Bonjour!
```

With doubled backslash to escape it in the text. See [parse](-companion/parse.md).

The class serializes to a plain string in the same format to make it easy to enter and edit by the organics.

To get a specific translations, use operator [get](get.md), which will get the translation or a fallback. To convert string to a multistring on the fly there is a convenience extension method

Normally you don't use the constructor unless you already have a map, otherwise use one of the convenience methods, as in this the sample.

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
   //sampleEnd
}
```

## Constructors

| | |
|---|---|
| [Multistring](-multistring.md) | [common]<br>constructor(values: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |
| [ParseException](-parse-exception/index.md) | [common]<br>class [ParseException](-parse-exception/index.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;can't parse&quot;, cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) : [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) |

## Properties

| Name | Summary |
|---|---|
| [default](default.md) | [common]<br>val [default](default.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>String for the default locale (or fallback locale) |
| [encoded](encoded.md) | [common]<br>val [encoded](encoded.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Encode to human-readable variant, suitable to [parse](-companion/parse.md), see [parse](-companion/parse.md) for details on the format. |
| [values](values.md) | [common]<br>val [values](values.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [get](get.md) | [common]<br>operator fun [get](get.md)(langCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
