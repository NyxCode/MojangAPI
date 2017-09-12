package de.nyxcode.mojangapi.internal

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.google.gson.GsonBuilder
import de.nyxcode.mojangapi.PlayerID
import de.nyxcode.mojangapi.PlayerProfile
import java.util.*
import java.util.concurrent.TimeUnit

internal val gson = GsonBuilder()
        .registerTypeAdapter(PlayerProfile::class.java, PlayerProfileDeserializer)
        .registerTypeAdapter(PlayerID::class.java, PlayerIDDeserializer)
        .create()

internal const val namesByIdURL = "https://api.mojang.com/user/profiles/%s/names"
internal const val idByNameURL = "https://api.mojang.com/users/profiles/minecraft/%s"
internal const val profileByIdURL = "https://sessionserver.mojang.com/session/minecraft/profile/%s"

internal const val SC_TOO_MANY_REQUESTS = 429

internal fun <K : Any, V : Any> createCache(weakKeys: Boolean,
                                            weakValues: Boolean,
                                            maxSize: Long,
                                            expiresAfter: Long,
                                            expiresAfterUnit: TimeUnit): Cache<K, V> {
    val builder = Caffeine.newBuilder()
    if (weakKeys)
        builder.weakKeys()
    if (weakValues)
        builder.weakValues()
    if (maxSize > 0) {
        builder.maximumSize(maxSize)
    }
    builder.expireAfterWrite(expiresAfter, expiresAfterUnit)
    return builder.build<K, V>()
}

internal fun UUID.toStringWithoutHyphens(): String {
    return toString().replace("-", "")
}

private val uuidHyphenRegex = Regex("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)")

internal fun String.toUUID(): UUID {
    val string = if (contains("-")) {
        this
    } else {
        replaceFirst(uuidHyphenRegex, "$1-$2-$3-$4-$5")
    }
    return UUID.fromString(string)
}

internal fun String.decodeBase64(): String = String(Base64.getDecoder().decode(this))