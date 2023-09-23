package net.sergeych.i18n

import net.sergeych.i18n.I18n.addString
import net.sergeych.i18n.I18n.addStrings
import net.sergeych.i18n.I18n.translate
import net.sergeych.sprintf.sprintf


/**
 * Translations engine, capable of:
 *
 * - Registering new language and translations on the fly: [addStrings], [addString]
 * - Translate `CharSequences` and format them: [I18n.translate], [t], [tf], [translate], etc.
 * - Retrieve proper translations packed in the data string itself, string with human-optimized forat, see [Multistring], [ms].
 *
 * @sample net.sergeych.i18n.I18nTest
 */
object I18n {
    /**
     * Internal locale index, hashable. Optimizes access to localization tables by
     * caching unique per-locale index of it. Attention, primary locale selector is
     * __language__, not country code.
     *
     * Important: when you create a locale, it's ok, but it is also registered in the
     * module adding new language if needed. We recommend reusing `Locale` instances, though
     * the impact of it is minimal.
     *
     * @param code ISO language code (two letters where there is the choice)
     */
    class Locale(val code: String) {

        val index = getOrAddLanguageIndexOf(code)

        override fun toString(): String = code

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Locale) return false

            if (code != other.code) return false

            return true
        }

        override fun hashCode(): Int {
            return code.hashCode()
        }

        /**
         * Translate a string using locale, same as [I18n.translate].
         */
        @Suppress("unused")
        fun t(str: String, vararg args: Any?) =
            translate(this, str, *args)

        /**
         * Translate a string using locale, same as [I18n.translate].
         */
        @Suppress("unused")
        fun translate(what: String,vararg args: Any?) = what.translate(this, *args)
    }

    var languages = listOf<String>()
        private set

//    private var strings = listOf(
//        null, "wrong encoded size: %s", "неверный формат размера: %s"
//    )

    private val stringsById = mutableMapOf<String, MutableList<String?>>()

    private val stringsByContent = mutableMapOf<String, MutableList<String?>>()

    /**
     * The default locale is used in functions and methods as the default value.
     * It is detected on each platform from the system locale set at the time
     * if module initialization. You can change it any time, so it will be used
     * program-wide
     */
    var defaultLocale: Locale = detectLocale()

    /**
     * The fallback locale is used when some translated item can't be found in the
     * desired locale. By default, it is set to system default locale detected.
     */
    var fallbackLocale: Locale = detectLocale()

    /**
     * Get the language index for a code, restructuring tables to add it if
     * it does not exist
     */
    private fun getOrAddLanguageIndexOf(code: String): Int {
        val i = languages.indexOf(code)
        if (i >= 0) return i
        addLanguage(code)
        return getOrAddLanguageIndexOf(code)
    }

    /**
     * Add new language if needed by restructuring inner tables.
     */
    private fun addLanguage(code: String) {
        if (code !in languages) {
            languages += code
            for (x in stringsById.values) {
                x.add(null)
            }
            for (v in stringsByContent.values) {
                v.add(null)
            }
        }
    }

    /**
     * Setting up the locales. We recommend doing it early but only in the main program,
     * better in `main` function, but not in the library module.
     *
     * The idea is, the main program initializes the locales and sets default and fallback,
     * or leave it to the system defaults (as set in OS or browser).
     *
     * Library modules instead should not initialize the library but add its own translations,
     * and better publish their translation ids somewhere so library users could add their
     * specific translations to their code. See [addString]'s note for library authors.
     *
     * @param langCodes list of language codes you mean to use
     * @param fallbackLangCode fallback language code; should be listed in [langCodes]
     * @param strings optional list of translations, see [addStrings] for format description.
     */
    fun setup(
        langCodes: List<String>,
        fallbackLangCode: String,
        strings: List<String?> = listOf()
    ) {
        if (fallbackLangCode !in langCodes)
            throw IllegalArgumentException("fallback language must be in languages")

        for (c in langCodes) {
            val code = c.lowercase()
            if (code !in languages)
                addLanguage(code)
        }

        fallbackLocale = Locale(fallbackLangCode)

        if (strings.isNotEmpty())
            addStrings(langCodes, strings)
    }

    /**
     * Add string translations for the specified language codes. Note that
     * the language codes should already set up with [setup].
     *
     * The [strings] parameter is an array of [langCodes].size + 1 columns where
     * columns are:
     *
     * | Column # | Data |
     * |----------|---------------------------------------------------|
     * | 0        |`stringId` or null                                 |
     * | 1        | translation for language [langCodes] at 0         |
     * | 2        | translation for language [langCodes] at 1, etc.]  |
     *
     * See [addString] for explanation on `stringId`
     *
     * @param langCodes languages in which strings are
     * @param strings array of [langCodes].size + 1 columns as described above.
     * @sample net.sergeych.i18n.I18nTest.translationTest
     */
    fun addStrings(langCodes: List<String>, strings: List<String?>) {
        val indices = langCodes.map { languages.indexOf(it) }

        strings.chunked(indices.size + 1).forEachIndexed { i, value ->
            val args = mutableListOf<Pair<String, String>>()
            if (value.all { it == null }) throw Exception("illegal string array, all nulls at line $i")
            for ((index, code) in langCodes.withIndex()) {
                value[index + 1]?.let { args += code to it }
            }
            addString(value[0], *args.toTypedArray())
        }
    }

    /**
     * Add string translation to table.
     *
     * __Note for library authors.__ It is safe to add translations in your languages, but we
     * recommend to use prefixed [id] like "mylibrary.nothingFound" to avoid clashes.
     * Also if you public the list of your ids, users could add translations in their applications
     * on the fly.
     *
     * @sample net.sergeych.i18n.I18nTest.addNewLangTest
     * @param id the id of the translation item, will be used first when translating.
     *      If not set, the first matching translation will be used that is not always good.
     *      We recommend to always ise ids.
     * @param variants translations in the format `"langCode" to "translation"`
     */
    fun addString(id: String? = null, vararg variants: Pair<String, String>) {
        // We need to correct our tables _before_ getting the list
        for( lc in variants ) { if( lc.first !in languages ) addLanguage(lc.first) }
        // Now we can
        val list = MutableList<String?>(languages.size) { null }
        list[0] = id
        for (s in variants) {
            val i = getOrAddLanguageIndexOf(s.first.lowercase())
            if (i < 0) throw IllegalArgumentException("unknown language code in $s")
            list[i] = s.second
        }
        if (id != null) stringsById[id] = list
        for (s in list) if (s != null) stringsByContent[s] = list
    }

    /**
     * Translate the string using specified locale, current fallback locale and
     * registered strings table, then, if any [args] are presented, transform it
     * with [sprintf](https://github.com/sergeych/mp_stools#sprintf-syntax-summary).
     * The module tries it best to find a translation for
     * a string, it searches for the translations in the following order:
     *
     * - using [format] as string ID (language-independent ID)
     * - looking for a [format] as a content string in some language
     *
     * Then it tries to find translation for the specified [locale], and then
     * in [I18n.fallbackLocale].
     *
     * If still nothing it found, it will use the source value of the [format].
     *
     * See [addString] and [addStrings] for registering translations and [Multistring]
     * to use translations packed in the source string.
     *
     * @param locale desired locale to translate to.
     * @param format string to translate, possible `sptintf` format (See above)
     * @param args if present, will be used to format the _translated result_ string using `sprintf`
     *          rules (see the link above)
     */
    fun translate(locale: Locale, format: String, vararg args: Any?): String {
        val list = stringsById[format] ?: stringsByContent[format]
        val t = list?.let { it[locale.index] ?: it[fallbackLocale.index] }
            ?: format
        return if (args.isNotEmpty())
            t.sprintf(*args)
        else t
    }
}

/**
 * Translate this sequence using [I18n.translate] and default locale, then use it as a format string
 * for `String.sptintf` if any [args] is present;
 * see [sprintf from included mp_stools](https://github.com/sergeych/mp_stools#sprintf-syntax-summary)
 * package.
 */
fun CharSequence.tf(vararg args: Any?) =
    I18n.translate(I18n.defaultLocale, (this as? String) ?: toString(), *args)

/**
 * Translate the sequence using a string tables using [I18n.translate]. If you need to
 * _select translation packed in this string_, use [ms] instead, otherwise see [Multistring].
 */
val CharSequence.t: String get() = tf()

/**
 * Translate this sequence as string with a specified locale, the same as [I18n.translate].
 * @sample net.sergeych.i18n.I18nTest.addNewLangTest
 */
fun CharSequence.translate(locale: I18n.Locale = I18n.defaultLocale, vararg args: Any?) =
    I18n.translate(locale, this.toString(), *args)
/**
 * Translate this sequence as string using a long code (not recommended),
 * the same as [I18n.translate].
 * @sample net.sergeych.i18n.I18nTest.addNewLangTest
 */
fun CharSequence.translate(langCode: String, vararg args: Any?) =
    I18n.translate(I18n.Locale(langCode), this.toString(), *args)