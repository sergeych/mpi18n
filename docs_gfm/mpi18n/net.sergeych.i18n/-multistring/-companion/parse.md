//[mpi18n](../../../../index.md)/[net.sergeych.i18n](../../index.md)/[Multistring](../index.md)/[Companion](index.md)/[parse](parse.md)

# parse

[common]\
fun [parse](parse.md)(definition: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ignoreErrors: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): [Multistring](../index.md)

Parse encoded human-friendly source like:

```kotlin
\en hi! \fr salut and a backslash \\ in text!
```

The rules are simple: the encoded human-writable string consists of language fragments `\\code text`. Code should be a 2-3 character ISO country code, in the example above it will split to:

- 
   French (fr): 'salut and a \ backslash in text!'
- 
   English (en): 'hi!'

As you see,you just double the backslash to include it in the text, and trailing and leadint spaces in the text are trimmed.
