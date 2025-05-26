package dev.zornov.bdengine.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("minecraft:text_display")
data class TextDisplay(
    @SerialName("text") val components: List<TextComponent>,
    @SerialName("text_opacity") val textOpacity: Int,
    val background: Int,
    val alignment: String,
    @SerialName("line_width") val lineWidth: Double,
    @SerialName("default_background") val defaultBackground: Boolean,
    override val transformation: List<Float>
) : Passenger()