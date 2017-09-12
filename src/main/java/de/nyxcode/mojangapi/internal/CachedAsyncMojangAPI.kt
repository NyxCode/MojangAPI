package de.nyxcode.mojangapi.internal

import de.nyxcode.mojangapi.AsyncMojangAPI
import de.nyxcode.mojangapi.PlayerID
import de.nyxcode.mojangapi.PlayerName
import de.nyxcode.mojangapi.PlayerProfile
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

internal class CachedAsyncMojangAPI(private val api: AsyncMojangAPI,
                                    weakReferences: Boolean,
                                    maxSize: Int,
                                    expiresAfter: Long,
                                    expiresAfterUnit: TimeUnit) : AsyncMojangAPI {

    private val namesCache = createCache<UUID, Array<PlayerName>>(
            weakReferences, weakReferences, maxSize.toLong(), expiresAfter, expiresAfterUnit)
    private val idCache =
            createCache<String, PlayerID>(weakReferences, weakReferences, maxSize.toLong(), expiresAfter, expiresAfterUnit)
    private val profileCache =
            createCache<UUID, PlayerProfile>(weakReferences, weakReferences, maxSize.toLong(), expiresAfter, expiresAfterUnit)

    override fun nameById(uuid: UUID): CompletableFuture<PlayerName> = namesById(uuid).thenApply { it.last() }

    override fun namesById(uuid: UUID): CompletableFuture<Array<PlayerName>> {
        val cached = namesCache.getIfPresent(uuid)
        if (cached != null) return CompletableFuture.completedFuture(cached)
        return api.namesById(uuid).apply {
            thenAccept {
                if (it != null) namesCache.put(uuid, it)
            }
        }
    }

    override fun idByName(name: String): CompletableFuture<PlayerID> {
        val cached = idCache.getIfPresent(name)
        if (cached != null) return CompletableFuture.completedFuture(cached)
        return api.idByName(name).apply {
            thenAccept {
                if (it != null) idCache.put(name, it)
            }
        }
    }

    override fun profileById(uuid: UUID): CompletableFuture<PlayerProfile> {
        val cached = profileCache.getIfPresent(uuid)
        if (cached != null) return CompletableFuture.completedFuture(cached)
        return api.profileById(uuid).apply {
            thenAccept {
                if (it != null) profileCache.put(uuid, it)
            }
        }
    }

    override fun close() = api.close()
}