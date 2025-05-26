package dev.zornov.bdengine.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextComponent(
    val text: String,
    val color: String,
    @SerialName("bold") val isBold: Boolean = false,
    @SerialName("italic") val isItalic: Boolean = false,
    @SerialName("underlined") val isUnderlined: Boolean = false,
    @SerialName("strikethrough") val isStrikethrough: Boolean = false,
    @SerialName("obfuscated") val isObfuscated: Boolean = false,
    val font: String
)