# Programmer-friendly KMP i18n

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

__Important note__. Initilaize the library _early and directly in the code_ so it wont conflict with possible static initializers in library classes. It means, call in main something like:

~~~kotlin
I18n.setup(listOf("en", "fr", "it", "ru"), "en")
~~~

If you do it in static initializer or like, be sure it will be called before any `addString` or other. It contains
simple tools to translate both the hardcoded string and the varying data and a simple way:

## Translating your data

When you need to enter and edit data in multiple languages (say to store it into the database) you can't include 
it in the source, you have a `Multistring`:

~~~kotlin
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

// If you need to format complex localized strings, sprintf is packed here:
assertEquals("Hello, Frank!",
    """\en Hello, %s! \fr Salut \\test\\ !"""
        .ms["en"]
        .sprintf("Frank"))

~~~

Multistring includes [kotlinx serialization](https://github.com/Kotlin/kotlinx.serialization) that uses the same format optimized for organics, so you can directly edit it in text fields and then serialize with whatever kotlinx serialization format you prefer.

It exports function from [mp_stools MP library](https://github.com/sergeych/mp_stools) so you can use sprintf and other
out of the box.

## Translations in the code

This is useful when your strings are hardcoded:

- `sprintf` - like interpolation:

```kotlin
I18n.addString("lvl%d", "en" to "Level %02d.1 percent", "it" to "Livello %02d.1 percento")

println("lvl%d".tf(17.22))
```

- easy to use: with extension functions for the `String`:

```kotlin
I18n.addString(null, "translated string:" to "en", "it" to "tradutto come:" )
println("translated string:".t)
```

- Simple bulk initialization:

```kotlin
import net.serheych.i18n.I18n

I18n.addStrings(
    listOf("en", "ru"), // languages in strings array 
    listOf(
        // ID, "en", "ru" as specified in the first parameter
        "id1", "foo", "фу",
        null, "bar", "бар",
        "id2", "buzz: %s", "базз: %s"
    )
)

I18n.fallbackLocale = I18n.Locale("ru")
```

```kotlin
// now it can be used with ids or any translated string,
// while id have the priority:

println("id1".t) //> "foo"
println("фу".t) //> "foo"
```

- works even when there are no registered transations:

```kotlin
println("just a new string".t) //> "just a new string"
```
Such orphans could be easily scanned; I'll add some tools to scan/collect missing translations.

- multiplatform locale detection

## Adding to your project:

```kotlin
repositories {
    // add this
    maven("https://maven.universablockchain.com")
}

// in dependencies add:
implementation("net.sergeych:mpi18n:0.3")
```

Voila ;)

## Missing features to be added

I am thinking of adding the idea of "coroutine context default locale" but yet can't understand
how to make default string functions to check for the current context. A vairant to add async
versions like `CharSecquence.tfa` looks clumsy and yet cryptic. And long names make it hard to use. welcome to the discussion though.

## Current status

Under active testing in production services. Consider it to be a beta.

__Under construction.__ 

## Planned features

- per-thread locale
- per coroutine context locale


