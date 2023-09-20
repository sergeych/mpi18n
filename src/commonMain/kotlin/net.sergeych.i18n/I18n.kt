package net.sergeych.tools.i18n

import net.sergeych.i18n.detectLocale
import net.sergeych.sprintf.sprintf


object I18n {
    class Locale(val code: String) {

        val index = getOrAddLanguageIndexOf(code)

        override fun toString(): String {
            return "Locale($code:$index)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Locale) return false

            if (code != other.code) return false

            return true
        }

        override fun hashCode(): Int {
            return code.hashCode()
        }

        @Suppress("unused")
        fun t(str: String, vararg args: Any?) =
            translate(this, str, *args)
    }

    var languages = listOf<String>()
        private set

//    private var strings = listOf(
//        null, "wrong encoded size: %s", "неверный формат размера: %s"
//    )

    private val stringsById = mutableMapOf<String, MutableList<String?>>()

    val stringsByContent = mutableMapOf<String, MutableList<String?>>()

    var defaultLocale: Locale = detectLocale()

    var fallbackLocale: Locale = detectLocale()

    fun getOrAddLanguageIndexOf(code: String): Int {
        val i = languages.indexOf(code)
        if (i >= 0) return i
        addLanguage(code)
        return getOrAddLanguageIndexOf(code)
    }

    fun addLanguage(code: String) {
        if (code !in languages) {
            languages += code
            for ( x in stringsById.values) {
                x.add(null)
            }
            for ( v in stringsByContent.values) {
                v.add(null)
            }
        }
    }

    fun setup(
        langCodes: List<String>,
        fallbackLangCode: String,
        strings: List<String?> = listOf()
    ) {
        if( fallbackLangCode !in langCodes)
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

    fun addStrings(langCodes: List<String>,strings: List<String?>) {
        val indices = langCodes.map { languages.indexOf(it) }

        strings.chunked(indices.size + 1).forEachIndexed { i, value ->
            val args = mutableListOf<Pair<String,String>>()
            if (value.all { it == null }) throw Exception("illegal string array, all nulls at line $i")
            for( (index, code) in langCodes.withIndex() ) {
                value[index+1]?.let { args += code to it }
            }
            addString(value[0], *args.toTypedArray())
        }
    }

    fun addString(id: String? = null, vararg variants: Pair<String, String>) {
        val list = MutableList<String?>(languages.size) { null }
        list[0] = id
        for (s in variants) {
            val i = getOrAddLanguageIndexOf(s.first.lowercase())
            if (i < 0) throw IllegalArgumentException("unknown language code in $s")
            list[i] = s.second
        }
        if (id != null) this.stringsById[id] = list
        for (s in list) if (s != null) stringsByContent[s] = list
    }
    fun translate(locale: Locale, format: String, vararg args: Any?): String {
        val list = stringsById[format] ?: stringsByContent[format]
        val t = list?.let { it[locale.index] ?: it[fallbackLocale.index] }
            ?: format
        return if (args.isNotEmpty())
            t.sprintf(*args)
        else t
    }
}

fun CharSequence.tf(vararg args: Any?) =
    I18n.translate(I18n.defaultLocale, (this as? String) ?: toString(), *args)

val CharSequence.t: String get() = tf()
