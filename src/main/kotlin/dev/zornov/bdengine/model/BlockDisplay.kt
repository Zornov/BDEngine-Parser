package dev.zornov.bdengine.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("minecraft:block_display")
data class BlockDisplay(
    @SerialName("block_state") val blockState: BlockState,
    override val transformation: List<Float>
) : Passenger()