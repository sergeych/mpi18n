package net.sergeych.i18n

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.sergeych.i18n.Multistring.Companion.parse

/**
 * Simple way to store translated things in __the data__ when you need it.
 *
 * The source format is rather simple:
 *
 *     \en Hello! \fr Bonjour!
 *
 * With doubled backslash to escape it in the text. See [parse].
 *
 * The class serializes to a plain string in the same format to make it easy to enter and
 * edit by the organics.
 *
 * To get a specific translations, use operator [get], which will get the translation or a
 * fallback. To convert string to a multistring on the fly there is a convenience extension method
 *
 * Normally you don't use the constructor unless you already have a map, otherwise use one of the
 * convenience methods, as in this the sample.
 *
 * @sample net.sergeych.i18n.I18nTest.testMultistring
 */
@Serializable(with=MultistringSerializer::class)
class Multistring(val values: Map<String, String>) {

    class ParseException(text: String = "can't parse", cause: Throwable? = null) :
        Exception(text, cause)

    operator fun get(langCode: String): String =
        values[langCode] ?: values[I18n.defaultLocale.code] ?: values.values.first()
    operator fun get(locale: I18n.Locale): String =
        values[locale.code] ?: values[I18n.defaultLocale.code] ?: values.values.first()


    /**
     * String for the default locale (or fallback locale)
     */
    @Suppress("unused")
    val default: String get() = this[I18n.defaultLocale]

    /**
     * Encode to human-readable variant, suitable to [parse], see [parse] for details on the
     * format.
     */
    val encoded: String by lazy {
        values.map { "[${it.key}]= ${it.value.replace("\\","\\\\")}" }.joinToString(" ")
    }

    companion object {

        val reDeinition = Regex("\\\\([A-Za-z]{2,3})(?:\\s|:)((?:(?![^\\\\]\\\\[A-Za-z]{2,3}).)*)")


        /**
         * Parse encoded human-friendly source like:
         *
         *      \en hi! \fr salut and a backslash \\ in text!
         *
         * The rules are simple:
         * the encoded human-writable string consists of language fragments `\\code text`. Code should be a 2-3
         * character ISO country code, in the example above it will split to:
         *
         * - French (fr): 'salut and a \ backslash in text!'
         * - English (en): 'hi!'
         *
         * As you see,you just double the backslash to include it in the text, and trailing and leadint
         * spaces in the text are trimmed.
         */
        fun parse(definition: String,ignoreErrors: Boolean = false): Multistring {
            return Multistring(reDeinition.findAll(definition).map {
                it.groupValues[1] to it.groupValues[2].trim().replace("\\\\", "\\")
            }.toMap().let {
                if (it.isEmpty()) {
                    if( ignoreErrors ) throw ParseException("can't parse '$definition'")
                    mapOf(I18n.defaultLocale.code to definition)
                }
                else
                    it
            })
        }

        /**
         * Same as [parse], syntax sugar to create multistring from an encoded string. See [parse]
         * on format.
         */
        operator fun invoke(definition: String): Multistring {
            return parse(definition)
        }
    }
}

/**
 * Transform the String object into Multistring using [Multistring.parse] format.
 */
val String.ms: Multistring get() = parse(this)


/**
 * Multistring is serialized as human-readable/editable string for convenience.
 * It also most often denser format than a Map.
 */
object MultistringSerializer : KSerializer<Multistring> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Multistring", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Multistring) {
        encoder.encodeString(value.encoded)
    }

    override fun deserialize(decoder: Decoder): Multistring {
        return parse(decoder.decodeString())
    }
}