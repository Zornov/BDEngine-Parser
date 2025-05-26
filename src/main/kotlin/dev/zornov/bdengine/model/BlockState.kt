package dev.zornov.bdengine.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockState(
    @SerialName("Name") val name: String,
    @SerialName("Properties") val properties: Map<String, String> = emptyMap()
)