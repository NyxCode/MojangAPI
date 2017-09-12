package de.nyxcode.mojangapi.internal

import de.nyxcode.mojangapi.*
import org.apache.http.HttpStatus.SC_NO_CONTENT
import org.apache.http.HttpStatus.SC_OK
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import java.util.*

internal class DefaultMojangAPI : MojangAPI {
    private var closed = false
    private val client = HttpClients.createMinimal()

    private inline fun <reified T : Any> jsonHttpGet(url: String): T? {
        check(!closed)
        val request = HttpGet(url)
        client.execute(request).use { response ->
            val status = response.statusLine.statusCode
            return when (status) {
                SC_OK -> response.entity.content.bufferedReader().use { reader ->
                    gson.fromJson(reader, T::class.java)
                }
                SC_NO_CONTENT -> null
                SC_TOO_MANY_REQUESTS -> throw TooManyRequestsException()
                else -> throw InvalidResponseException("Unexpected status $status")
            }
        }
    }

    override fun nameById(uuid: UUID) = nameByIdOrNull(uuid) ?: throw PlayerNotFoundException(uuid)

    override fun nameByIdOrNull(uuid: UUID) = namesByIdOrNull(uuid)?.firstOrNull()


    override fun namesById(uuid: UUID) = namesByIdOrNull(uuid) ?: throw PlayerNotFoundException(uuid)

    override fun namesByIdOrNull(uuid: UUID): Array<PlayerName>? {
        check(!closed)
        val id = uuid.toStringWithoutHyphens()
        val url = namesByIdURL.format(id)
        return jsonHttpGet(url)
    }


    override fun idByName(name: String) = idByNameOrNull(name) ?: throw PlayerNotFoundException(name)

    override fun idByNameOrNull(name: String): PlayerID? {
        check(!closed)
        val url = idByNameURL.format(name)
        return jsonHttpGet(url)
    }


    override fun profileById(uuid: UUID) = profileByIdOrNull(uuid) ?: throw PlayerNotFoundException(uuid)

    override fun profileByIdOrNull(uuid: UUID): PlayerProfile? {
        check(!closed)
        val id = uuid.toStringWithoutHyphens()
        val url = profileByIdURL.format(id)
        return jsonHttpGet(url)
    }


    override fun close() {
        check(!closed)
        client.close()
        closed = true
    }
}