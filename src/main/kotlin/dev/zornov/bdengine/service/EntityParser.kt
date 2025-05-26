package dev.zornov.bdengine.service

import dev.zornov.bdengine.model.BdCommand
import kotlinx.serialization.json.Json

class EntityParser {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        classDiscriminator = "id"
    }

    fun parseEntity(nbtPart: String): BdCommand {
        val withoutF = nbtPart.replace(Regex("([-\\d.]+)f"), "$1")
        val jsonLike = withoutF.replace(
            Regex("(?<=[\\{,])([A-Za-z0-9_]+):")
        ) { m -> "\"${m.groupValues[1]}\":" }

        return json.decodeFromString(jsonLike)
    }

    fun parsePassengers(nbtPart: String): BdCommand =
        parseEntity(nbtPart)

    fun parseCommand(fullCommand: String): BdCommand {
        val idx = fullCommand.indexOf('{')
        require(idx >= 0) { "Не найдена NBT-часть в команде" }
        val nbtPart = fullCommand.substring(idx)
        return parseEntity(nbtPart)
    }
}