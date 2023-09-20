# Programmer-friendly KMP i18n

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

__Important note__. Initilaize the library _early and directly in the code_ so it wont conflict with possible static initializers in library classes. It means, call in main something like:

~~~kotlin
I18n.setup(listOf("en", "fr", "it", "ru"), "en")
~~~

If you do it in static initializer or like, be sure it will be called before any `addString` or other. 

Key features are

- `sprintf` - like interpolation:

```kotlin
I18n.addString("lvl%d", "en" to "Level %02d.1 percent", "it" to "Livello %02d.1 percento")

println("lvl%d".tf(17.22))
```

- easy to use:

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

- universal, simple usage:

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
implementation("net.sergeych:mpi18n:0.2-SNAPSHOT")
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


