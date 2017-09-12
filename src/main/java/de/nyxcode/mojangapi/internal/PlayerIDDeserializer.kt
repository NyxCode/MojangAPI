package de.nyxcode.mojangapi.internal

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import de.nyxcode.mojangapi.PlayerID
import java.lang.reflect.Type

internal object PlayerIDDeserializer : JsonDeserializer<PlayerID> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): PlayerID {
        val obj = json.asJsonObject
        return PlayerID(id = obj["id"].asString.toUUID(),
                name = obj["name"].asString)
    }
}