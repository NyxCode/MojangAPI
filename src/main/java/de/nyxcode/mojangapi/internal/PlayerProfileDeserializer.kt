package de.nyxcode.mojangapi.internal

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import de.nyxcode.mojangapi.PlayerProfile
import java.lang.reflect.Type

internal object PlayerProfileDeserializer : JsonDeserializer<PlayerProfile> {
    private val parser = JsonParser()

    override fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): PlayerProfile {
        val obj = json.asJsonObject
        val texturesProperty = obj.getAsJsonArray("properties")
                ?.first { it.asJsonObject.get("name").asString == "textures" }
                ?.asJsonObject
                ?.get("value")
                ?.asString
                ?.decodeBase64()
        val textures = if (texturesProperty == null) {
            null
        } else {
            parser.parse(texturesProperty)?.asJsonObject?.getAsJsonObject("textures")
        }

        return PlayerProfile(
                id = obj["id"].asString.toUUID(),
                name = obj.get("name").asString,
                skinUrl = textures?.get("SKIN")?.asJsonObject?.get("url")?.asString,
                capeUrl = textures?.get("CAPE")?.asJsonObject?.get("url")?.asString)
    }
}