package dev.zornov.bdengine.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BdCommand(
    @SerialName("Passengers") val passengers: List<Passenger>
)