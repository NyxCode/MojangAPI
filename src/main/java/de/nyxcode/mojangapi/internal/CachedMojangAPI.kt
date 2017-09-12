package de.nyxcode.mojangapi.internal

import de.nyxcode.mojangapi.*
import java.util.*
import java.util.concurrent.TimeUnit

internal class CachedMojangAPI(private val api: MojangAPI,
                               weakReferences: Boolean,
                               maxSize: Int,
                               expiresAfter: Long,
                               expiresAfterUnit: TimeUnit) : MojangAPI {

    private val namesCache = createCache<UUID, Array<PlayerName>>(
            weakReferences, weakReferences, maxSize.toLong(), expiresAfter, expiresAfterUnit)
    private val idCache = createCache<String, PlayerID>(
            weakReferences, weakReferences, maxSize.toLong(), expiresAfter, expiresAfterUnit)
    private val profileCache = createCache<UUID, PlayerProfile>(
            weakReferences, weakReferences, maxSize.toLong(), expiresAfter, expiresAfterUnit)

    override fun nameById(uuid: UUID) = nameByIdOrNull(uuid) ?: throw PlayerNotFoundException(uuid)

    override fun nameByIdOrNull(uuid: UUID) = namesByIdOrNull(uuid)?.lastOrNull()


    override fun namesById(uuid: UUID) = namesByIdOrNull(uuid) ?: throw PlayerNotFoundException(uuid)

    override fun namesByIdOrNull(uuid: UUID) = namesCache.get(uuid) { api.namesByIdOrNull(uuid) }


    override fun idByName(name: String) = idByNameOrNull(name) ?: throw PlayerNotFoundException(name)

    override fun idByNameOrNull(name: String) = idCache.get(name) { api.idByNameOrNull(name) }


    override fun profileById(uuid: UUID) = profileByIdOrNull(uuid) ?: throw PlayerNotFoundException(uuid)

    override fun profileByIdOrNull(uuid: UUID) = profileCache.get(uuid) { api.profileByIdOrNull(uuid) }


    override fun close() = api.close()
}