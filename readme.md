# Programmer-friendly KMP i18n

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

__Under construction.__ 

## Planned features

- per-thread locale
- per coroutine context locale


