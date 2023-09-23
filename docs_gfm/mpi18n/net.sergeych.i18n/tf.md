//[mpi18n](../../index.md)/[net.sergeych.i18n](index.md)/[tf](tf.md)

# tf

[common]\
fun [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html).[tf](tf.md)(vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Translate this sequence using [I18n.translate](-i18n/translate.md) and default locale, then use it as a format string for `String.sptintf` if any [args](tf.md) is present; see [sprintf from included mp_stools](https://github.com/sergeych/mp_stools#sprintf-syntax-summary) package.
