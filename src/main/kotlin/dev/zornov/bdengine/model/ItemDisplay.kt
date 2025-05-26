package dev.zornov.bdengine.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("minecraft:item_display")
data class ItemDisplay(
    @SerialName("item") val item: Item,
    override val transformation: List<Float>
) : Passenger()