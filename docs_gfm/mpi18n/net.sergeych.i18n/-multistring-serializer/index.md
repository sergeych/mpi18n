//[mpi18n](../../../index.md)/[net.sergeych.i18n](../index.md)/[MultistringSerializer](index.md)

# MultistringSerializer

[common]\
object [MultistringSerializer](index.md) : KSerializer&lt;[Multistring](../-multistring/index.md)&gt; 

Multistring is serialized as human-readable/editable string for convenience. It also most often denser format than a Map.

## Properties

| Name | Summary |
|---|---|
| [descriptor](descriptor.md) | [common]<br>open override val [descriptor](descriptor.md): SerialDescriptor |

## Functions

| Name | Summary |
|---|---|
| [deserialize](deserialize.md) | [common]<br>open override fun [deserialize](deserialize.md)(decoder: Decoder): [Multistring](../-multistring/index.md) |
| [serialize](serialize.md) | [common]<br>open override fun [serialize](serialize.md)(encoder: Encoder, value: [Multistring](../-multistring/index.md)) |
